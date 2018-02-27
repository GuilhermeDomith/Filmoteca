package ti.lpv.filmoteca.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import ti.lpv.filmoteca.dao.ArtistaDao;
import ti.lpv.filmoteca.dao.AutorDao;
import ti.lpv.filmoteca.dao.DiretorDao;
import ti.lpv.filmoteca.dao.GeneroDao;
import ti.lpv.filmoteca.dao.PaisDao;
import ti.lpv.filmoteca.modelo.Artista;
import ti.lpv.filmoteca.modelo.Autor;
import ti.lpv.filmoteca.modelo.Diretor;
import ti.lpv.filmoteca.modelo.Genero;
import ti.lpv.filmoteca.modelo.Pais;
import ti.lpv.filmoteca.modelo.RotuloAdicionar;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.border.TitledBorder;

import java.awt.Color;

import javax.swing.border.EtchedBorder;
import javax.swing.SwingConstants;
import javax.swing.ListSelectionModel;

public class IgAdicionar extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JList listDadosBD;
	private JList listDadosSelecionados;
	private List<Artista> artistasBD, artistasSelecionados;
	private List<Genero> generosBD, generosSelecionados;
	private List<Autor> autoresBD, autoresSelecionados;
	private List<Diretor> diretoresBD, diretoresSelecionados;
	private List<Pais> paisesBD, paisesSelecionados;
	private List<String> dadosSelecionadosString;
	private RotuloAdicionar rotuloAdicionar;
	private IgCadastrar igCadastrar;

	
	/**
	 * Create the dialog.
	 */
	public IgAdicionar(IgCadastrar igCadastrar, RotuloAdicionar rotuloAdicionar) {
		
		this.igCadastrar = igCadastrar;
		this.rotuloAdicionar = rotuloAdicionar;
		
		setSize(496, 403);
		setLocationRelativeTo(igCadastrar);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(70, 130, 180));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		setTitle(rotuloAdicionar.getTituloJanela());
		
		//Cria os rótulos da janela.
		JLabel labelTituloJanela = new JLabel(rotuloAdicionar.getTituloJanela());
		labelTituloJanela.setForeground(Color.WHITE);
		labelTituloJanela.setHorizontalAlignment(SwingConstants.CENTER);
		labelTituloJanela.setFont(new Font("Eras Bold ITC", Font.BOLD, 18));
		labelTituloJanela.setBounds(6, 12, 468, 14);
		contentPanel.add(labelTituloJanela);
		
		
		/*Cria os JList que exibirão os dados salvos no BD e os escolhidos
		 * para serem adicionados ao filme.
		 */
		
		JButton buttonAdicionar = new JButton("Adicionar >>>");
		buttonAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				adicionarObjeto();
			}
		});
		buttonAdicionar.setBounds(174, 108, 126, 38);
		contentPanel.add(buttonAdicionar);
		
		JButton buttonRemover = new JButton("<<< Remover ");
		buttonRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				removeObjeto();
			}
		});
		buttonRemover.setBounds(174, 158, 126, 38);
		contentPanel.add(buttonRemover);
		
		//Cria o botão que permite adicionar um novo dado ao BD.
		JButton novoButton = new JButton("Novo");
		novoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				novoDadoBD();
			}
		});
		novoButton.setBounds(19, 277, 126, 38);
		contentPanel.add(novoButton);
		
		//Cria o painel que exibe os dados do BD e adiciona ods componentes.
		JPanel painelDadosBD = new JPanel();
		painelDadosBD.setBackground(new Color(51, 102, 153));
		painelDadosBD.setBorder(new TitledBorder(null, "<html>"+rotuloAdicionar.getLabelLista()+"<br/>Cadastrados</html>", TitledBorder.LEADING, TitledBorder.TOP, null, Color.WHITE));
		painelDadosBD.setBounds(13, 39, 150, 240);
		contentPanel.add(painelDadosBD);
		painelDadosBD.setLayout(new BorderLayout(0, 0));
		
		listDadosBD = new JList();
		listDadosBD.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane jsp = new JScrollPane(listDadosBD);
		painelDadosBD.add(jsp,BorderLayout.CENTER);
		
		
		//Cria o painel que exibe os dados já selecionados e adiciona os componentes.
		JPanel painelDadosSelecionados = new JPanel();
		painelDadosSelecionados.setBackground(new Color(51, 102, 153));
		painelDadosSelecionados.setBorder(new TitledBorder(null, "<html>Adicionados<br/>ao Filme</html>", TitledBorder.LEADING, TitledBorder.TOP, null, Color.WHITE));
		painelDadosSelecionados.setBounds(312, 39, 150, 240);
		contentPanel.add(painelDadosSelecionados);
		painelDadosSelecionados.setLayout(new BorderLayout(0, 0));
		
		listDadosSelecionados = new JList();
		listDadosSelecionados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane jsp2 = new JScrollPane(listDadosSelecionados);
		painelDadosSelecionados.add(jsp2,BorderLayout.CENTER);
		
		obterDadosLista();
		atualizaJList();
		
		//Cria o painel de botões.
		JPanel buttonPane = new JPanel();
		buttonPane.setBackground(new Color(70, 130, 180));
		buttonPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton fecharButton = new JButton("Fechar");
		fecharButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		buttonPane.add(fecharButton);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				dispose();
			}
		});
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setModal(true);
		setVisible(true);
	}
	
	/**
	 * Obtem os dados no BD e os dados já selecionados, e os adiciona ao ArrayList 
	 * de acordo com o tipo de Rotulo.
	 */
	private void obterDadosLista(){
	
		if(rotuloAdicionar == RotuloAdicionar.ARTISTA){
			artistasSelecionados = igCadastrar.getArtistas();
			artistasBD = new ArrayList<Artista>();
			artistasBD = new ArtistaDao().listarArtistas();
		}else{if(rotuloAdicionar == RotuloAdicionar.AUTOR){
				autoresSelecionados = igCadastrar.getAutores();
				autoresBD = new ArrayList<Autor>();
				autoresBD = new AutorDao().listarAutores();
			}else{if(rotuloAdicionar == RotuloAdicionar.DIRETOR){
					diretoresSelecionados = igCadastrar.getDiretores();
					diretoresBD = new ArrayList<Diretor>();
					diretoresBD = new DiretorDao().listarDiretores();
				}else{if(rotuloAdicionar == RotuloAdicionar.GENERO){
						generosSelecionados = igCadastrar.getGeneros();
						generosBD = new ArrayList<Genero>();
						generosBD = new GeneroDao().listarGeneros();
						removerGenerosPadroes();
					}else{
						paisesSelecionados = igCadastrar.getPaises();
						paisesBD = new ArrayList<Pais>();
						paisesBD = new PaisDao().listarPaises();
					}
				}
			}
		}
	}
	
	/**
	 * Remove da lista os generos que serão adicionados por outro método. 
	 */
	private void removerGenerosPadroes(){
		Long codigoGenero[] = {1L,2L,3L,4L,5L,6L,7L};
		
		for(int x=0; x < codigoGenero.length; x++){
		
			for(int i=0; i < generosBD.size(); i++){
				Genero g = generosBD.get(i);
				if(g.getCodigoGenero() == codigoGenero[x]){
					generosBD.remove(i);
					break;
				}
			}
		}
			
	}
	
	/**
	 * Atualiza as JList para exibir os novos dados adicinados ao array 
	 * de cada JList.
	 */
	private void atualizaJList(){
		
		List<String> dadosBD = new ArrayList<String>(),
				dadosSelecionados = new ArrayList<String>();
		
		organizarListasAlfabeticamente();
		verficarDadosSelecionados();
		obterDadosEmString(dadosBD,dadosSelecionados);
		
		listDadosBD.setListData(dadosBD.toArray());
		listDadosSelecionados.setListData(dadosSelecionados.toArray());

	}
	
	/**
	 * Obtem os dados de um ArrayList de objetos e os adiciona em outro ArrayList de String.
	 */
	private void obterDadosEmString(List<String> dadosBD, List<String> dadosSelecionados){
		if(rotuloAdicionar == RotuloAdicionar.ARTISTA){
			for(Artista a : artistasBD)
				dadosBD.add(a.getNome());
			
			for(Artista a : artistasSelecionados)
				dadosSelecionados.add(a.getNome());
		}else{if(rotuloAdicionar == RotuloAdicionar.AUTOR){
				for(Autor a : autoresBD)
					dadosBD.add(a.getNome());
				
				for(Autor a : autoresSelecionados)
					dadosSelecionados.add(a.getNome());
			}else{if(rotuloAdicionar == RotuloAdicionar.DIRETOR){
					for(Diretor d : diretoresBD)
						dadosBD.add(d.getNome());
					
					for(Diretor d : diretoresSelecionados)
						dadosSelecionados.add(d.getNome());
				}else{if(rotuloAdicionar == RotuloAdicionar.GENERO){
					for(Genero g : generosBD)
						dadosBD.add(g.getDescricao());
					
					for(Genero g : generosSelecionados)
						dadosSelecionados.add(g.getDescricao());
					}else{
						for(Pais p : paisesBD)
							dadosBD.add(p.getNome());
						
						for(Pais p : paisesSelecionados)
							dadosSelecionados.add(p.getNome());
					
					}
				}
			}
		}
		
		this.dadosSelecionadosString = dadosSelecionados;
	}
	
	/**
	 * Organiza alfabeticamente os ArrayList de acordo com com o rotulo.
	 */
	private void organizarListasAlfabeticamente(){
		if(rotuloAdicionar == RotuloAdicionar.ARTISTA){
			organizarArtista(artistasBD);
			organizarArtista(artistasSelecionados);
		}else{if(rotuloAdicionar == RotuloAdicionar.AUTOR){
			organizarAutor(autoresBD);
			organizarAutor(autoresSelecionados);
			}else{if(rotuloAdicionar == RotuloAdicionar.DIRETOR){
				organizarDiretor(diretoresBD);
				organizarDiretor(diretoresSelecionados);
				}else{if(rotuloAdicionar == RotuloAdicionar.GENERO){
						organizarGenero(generosBD);
						organizarGenero(generosSelecionados);
					}else{
						organizarPais(paisesBD);
						organizarPais(paisesSelecionados);
					}
				}
			}
		}
	}
	
	/**
	 * Organiza alfabeticamente o ArrayList de artistas.
	 */
	private void organizarArtista(List<Artista> artistas){
			
		Collections.sort(artistas, new Comparator<Artista>() {
			public int compare(Artista artista1,Artista artista2) {
				return artista1.getNome().compareTo( artista2.getNome() );
			};
		});
	}
	
	/**
	 * Organiza alfabeticamente o ArrayList de autores.
	 */
	private void organizarAutor(List<Autor> autores){

		Collections.sort(autores, new Comparator<Autor>() {
			public int compare(Autor autor1, Autor autor2  ) {
				return autor1.getNome().compareTo( autor2.getNome() );
			};
		});
	}
	
	/**
	 * Organiza alfabeticamente o ArrayList de diretores.
	 */
	private void organizarDiretor(List<Diretor> diretores){

		Collections.sort(diretores, new Comparator<Diretor>() {
			public int compare(Diretor diretor1,Diretor diretor2) {
				return diretor1.getNome().compareTo( diretor2.getNome() );
			};
		});
	}
	
	/**
	 * Organiza alfabeticamente o ArrayList de generos.
	 */
	private void organizarGenero(List<Genero> generos){

		Collections.sort(generos, new Comparator<Genero>() {
			public int compare(Genero genero1,Genero genero2) {
				return genero1.getDescricao().compareTo( genero2.getDescricao() );
			};
		});
	}
	
	/**
	 * Organiza alfabeticamente o ArrayList de países.
	 */
	private void organizarPais(List<Pais> paises){

		Collections.sort(paises, new Comparator<Pais>() {
			public int compare(Pais pais1,Pais pais2) {
				return pais1.getNome().compareTo( pais2.getNome());
			};
		});
	}
	
	/**
	 * Remove o objeto selecionado na 'listSalvosBD' e o adiciona ao 'listAdicionados'.
	 */
	private void adicionarObjeto(){
		//Obtem o indice do objeto selecionado no JList.
		int indice = listDadosBD.getSelectedIndex();
		if(indice == -1) return;
		
		if(rotuloAdicionar == RotuloAdicionar.ARTISTA){
			artistasSelecionados.add(artistasBD.get(indice));
			artistasBD.remove(indice);
		}else{if(rotuloAdicionar == RotuloAdicionar.AUTOR){
			autoresSelecionados.add(autoresBD.get(indice));
			autoresBD.remove(indice);
			}else{if(rotuloAdicionar == RotuloAdicionar.DIRETOR){
				diretoresSelecionados.add(diretoresBD.get(indice));
				diretoresBD.remove(indice);
				}else{if(rotuloAdicionar == RotuloAdicionar.GENERO){
						generosSelecionados.add(generosBD.get(indice));
						generosBD.remove(indice);
					}else{
						paisesSelecionados.add(paisesBD.get(indice));
						paisesBD.remove(indice);
					}
				}
			}
		}
		
		atualizaJList();
	}
	
	/**
	 * Remove o objeto selecionado na 'listAdicionados' e o adiciona ao 'listSalvosBD'.
	 */
	private void removeObjeto(){
		//Obtem o indice do objeto selecionado no JList.
		int indice = listDadosSelecionados.getSelectedIndex();
		if(indice == -1) return;
		
		if(rotuloAdicionar == RotuloAdicionar.ARTISTA){
			artistasBD.add(artistasSelecionados.get(indice));
			artistasSelecionados.remove(indice);
		}else{if(rotuloAdicionar == RotuloAdicionar.AUTOR){
				autoresBD.add(autoresSelecionados.get(indice));
				autoresSelecionados.remove(indice);
			}else{if(rotuloAdicionar == RotuloAdicionar.DIRETOR){
					diretoresBD.add(diretoresSelecionados.get(indice));
					diretoresSelecionados.remove(indice);
				}else{if(rotuloAdicionar == RotuloAdicionar.GENERO){
						generosBD.add(generosSelecionados.get(indice));
						generosSelecionados.remove(indice);
					}else{
						paisesBD.add(paisesSelecionados.get(indice));
						paisesSelecionados.remove(indice);
					}
				}
			}
		}
		
		atualizaJList();
	}
	
	
	/**
	 * Verifica quais os dados estão selecionados, para serem removidos do Arraylist que 
	 * contém os dados obtidos do BD. verificando de acordo com o rotulo.
	 */
	private void verficarDadosSelecionados(){
		if(rotuloAdicionar == RotuloAdicionar.ARTISTA){
			verificarArtistaSelecionado();
		}else{if(rotuloAdicionar == RotuloAdicionar.AUTOR){
				verificarAutorSelecionado();
			}else{if(rotuloAdicionar == RotuloAdicionar.DIRETOR){
					verificarDiretorSelecionado();
				}else{if(rotuloAdicionar == RotuloAdicionar.GENERO){
						verificarGeneroSelecionado();
					}else{
						verficarPaisSelecionado();
					}
				}
			}
		}
				
	}
	
	/**
	 * Verifica quais os artistas estão contidos no ArrayList artistasSelecionados, 
	 * para serem removidos do Arraylist artistasBD.
	 */
	private void verificarArtistaSelecionado(){

		for(Artista a : artistasSelecionados){
			int indice = -1;
			
			for(int i=0; i < artistasBD.size(); i++){
				Artista a2 = artistasBD.get(i);
				if(a2.getCodigoArtista() == a.getCodigoArtista()){
					indice = i;
					break;
				}
			}
			
			if(indice != -1)
				artistasBD.remove(indice);
		}
	}
	
	/**
	 * Verifica quais os autores estão contidos no ArrayList autoresSelecionados, 
	 * para serem removidos do Arraylist autoresBD.
	 */
	private void verificarAutorSelecionado(){

		for(Autor a : autoresSelecionados){
			int indice = -1;
			
			for(int i=0; i < autoresBD.size(); i++){
				Autor a2 = autoresBD.get(i);
				if(a2.getCodigoAutor() == a.getCodigoAutor()){
					indice = i;
					break;
				}
			}
			
			if(indice != -1)
				autoresBD.remove(indice);
		}
	}
	
	/**
	 * Verifica quais os Diretores estão contidos no ArrayList diretoresSelecionados, 
	 * para serem removidos do Arraylist diretoresBD.
	 */
	private void verificarDiretorSelecionado(){

		for(Diretor d : diretoresSelecionados){
			int indice = -1;
			
			for(int i=0; i < diretoresBD.size(); i++){
				Diretor d2 = diretoresBD.get(i);
				if(d2.getCodigoDiretor() == d.getCodigoDiretor()){
					indice = i;
					break;
				}
			}
			
			if(indice != -1)
				diretoresBD.remove(indice);
		}
	}
	
	/**
	 * Verifica quais os Generos estão contidos no ArrayList generosSelecionados, 
	 * para serem removidos do Arraylist generosBD.
	 */
	private void verificarGeneroSelecionado(){

		for(Genero g : generosSelecionados){
			int indice = -1;
			
			for(int i=0; i < generosBD.size(); i++){
				Genero a2 = generosBD.get(i);
				if(a2.getCodigoGenero() == g.getCodigoGenero()){
					indice = i;
					break;
				}
			}
			
			if(indice != -1)
				generosBD.remove(indice);
		}
	}
	
	/**
	 * Verifica quais os Países estão contidos no ArrayList paisesSelecionados, 
	 * para serem removidos do Arraylist paisesBD.
	 */
	private void verficarPaisSelecionado(){

		for(Pais p : paisesSelecionados){
			int indice = -1;
			
			for(int i=0; i < paisesBD.size(); i++){
				Pais p2 = paisesBD.get(i);
				if(p2.getCodigoPais() == p.getCodigoPais()){
					indice = i;
					break;
				}
			}
			
			if(indice != -1)
				paisesBD.remove(indice);
		}
	}
	
	/**
	 * Adiciona um novo dado ao banco de dados.
	 */
	private void novoDadoBD(){
		String nomeOuDescricao = JOptionPane.showInputDialog(this,rotuloAdicionar.getMensagem(),
				rotuloAdicionar.getTituloJanela2(), JOptionPane.QUESTION_MESSAGE);
		
		if(nomeOuDescricao == null || nomeOuDescricao.isEmpty()) return;
		
		if(rotuloAdicionar == RotuloAdicionar.ARTISTA){
			novoArtista(nomeOuDescricao);
		}else{if(rotuloAdicionar == RotuloAdicionar.AUTOR){
				novoAutor(nomeOuDescricao);
			}else{if(rotuloAdicionar == RotuloAdicionar.DIRETOR){
					novoDiretor(nomeOuDescricao);
				}else{if(rotuloAdicionar == RotuloAdicionar.GENERO){
						novoGenero(nomeOuDescricao);
					}else{
						novoPais(nomeOuDescricao);
					}
				}
			}
		}
		
	}
	
	/**
	 * Adiciona um novo Artista ao banco de dados.
	 */
	private void novoArtista(String nome){
		
		Artista artista = new Artista();
		artista.setNome(nome);
		new ArtistaDao().adicionar(artista);
		obterDadosLista();
		atualizaJList();
	}
	
	/**
	 * Adiciona um novo Autor ao banco de dados.
	 */
	private void novoAutor(String nome){
		
		Autor autor = new Autor();
		autor.setNome(nome);
		new AutorDao().adicionar(autor);
		obterDadosLista();
		atualizaJList();
	}
	
	/**
	 * Adiciona um novo Diretor ao banco de dados.
	 */
	private void novoDiretor(String nome){
		
		Diretor diretor = new Diretor();
		diretor.setNome(nome);
		new DiretorDao().adicionar(diretor);
		obterDadosLista();
		atualizaJList();
	}
	
	/**
	 * Adiciona um novo Genero ao banco de dados.
	 */
	private void novoGenero(String descricao){
		
		Genero genero = new Genero();
		genero.setDescricao(descricao);
		new GeneroDao().adicionar(genero);
		obterDadosLista();
		atualizaJList();
	}
	
	/**
	 * Adiciona um novo País ao banco de dados.
	 */
	private void novoPais(String nome){
		Pais pais = new Pais();
		pais.setNome(nome);
		new PaisDao().adicionar(pais);
		obterDadosLista();
		atualizaJList();
	}
	
	
	public List<String> getDadosSelecionadosString() {
		return dadosSelecionadosString;
	}

	public List<Artista> getArtistasSelecionados() {
		return artistasSelecionados;
	}

	public List<Genero> getGenerosSelecionados() {
		return generosSelecionados;
	}

	public List<Autor> getAutoresSelecionados() {
		return autoresSelecionados;
	}

	public List<Diretor> getDiretoresSelecionados() {
		return diretoresSelecionados;
	}

	public List<Pais> getPaisesSelecionados() {
		return paisesSelecionados;
	}
	
}
