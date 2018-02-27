package ti.lpv.filmoteca.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import ti.lpv.filmoteca.dao.ArtistaDao;
import ti.lpv.filmoteca.dao.AutorDao;
import ti.lpv.filmoteca.dao.DiretorDao;
import ti.lpv.filmoteca.dao.FilmeDao;
import ti.lpv.filmoteca.dao.GeneroDao;
import ti.lpv.filmoteca.modelo.Artista;
import ti.lpv.filmoteca.modelo.Autor;
import ti.lpv.filmoteca.modelo.Diretor;
import ti.lpv.filmoteca.modelo.Filme;
import ti.lpv.filmoteca.modelo.Genero;
import ti.lpv.filmoteca.modelo.RotuloConsultar;

public class IgConsultar extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldPesquisa;
	private JLabel labelErro;
	private RotuloConsultar rotuloConsultar;
	private List<Filme> filmes;
	private JPanel painelFilmes;
	private JScrollPane scrollPaneFilmes;
	private IgJanelaPrincipal janelaReferencia;


	/**
	 * Cria a caixa de diálogo onde será possível consultar um artista, autor
	 * diretor, filme ou gênero dependendo da função executada, que deverá ser passada 
	 * por parametro no contrutor da classe. Assim exibirá os filmes ligados à consulta.
	 * Caso não encontre nenhuma informação exibe uma mensagem informando.
	 */
	public IgConsultar(IgJanelaPrincipal janelaReferencia,RotuloConsultar rotuloConsultar) {
		setTitle(rotuloConsultar.getTituloJanela());
		setSize(784, 577);
		this.rotuloConsultar = rotuloConsultar;
		this.janelaReferencia = janelaReferencia;
		setLocationRelativeTo(janelaReferencia);
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		contentPanel.setBackground(new Color(70, 130, 180));
		
		JLabel labelFornecaNome = new JLabel(rotuloConsultar.getMensagem());
		labelFornecaNome.setForeground(new Color(255, 255, 255));
		labelFornecaNome.setFont(new Font("Eras Demi ITC", Font.BOLD, 12));
		labelFornecaNome.setBounds(10, 11, 239, 14);
		contentPanel.add(labelFornecaNome);
		
		labelErro = new JLabel();
		labelErro.setHorizontalAlignment(SwingConstants.CENTER);
		labelErro.setFont(new Font("Eras Demi ITC", Font.BOLD, 22));
		labelErro.setForeground(Color.RED);
		
		textFieldPesquisa = new JTextField();
		textFieldPesquisa.setBounds(10, 28, 197, 27);
		contentPanel.add(textFieldPesquisa);
		
		JButton buttonPesquisar = new JButton("Pesquisar");
		buttonPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				painelFilmes.removeAll();
				if(textFieldPesquisa.getText().equals("")){
					labelErro.setText("Forne\u00E7a algum valor no campo de pesquisa!");
					painelFilmes.add(labelErro);
				}else{
					pesquisar();
				}
			}
		});
		buttonPesquisar.setBounds(219, 23, 126, 38);
		getRootPane().setDefaultButton(buttonPesquisar);
		contentPanel.add(buttonPesquisar);
		
		//Cria o painel que exibe os filmes
		painelFilmes = new JPanel();
		painelFilmes.setBackground(new Color(51, 102, 153));
		
		scrollPaneFilmes = new JScrollPane(painelFilmes);
		scrollPaneFilmes.setBounds(2, 90, 776, 427);
		contentPanel.add(scrollPaneFilmes);
		
		setResizable(false);
		setModal(true);
		setVisible(true);
		
	}//construtor IgConsultar
	
	private void pesquisar(){
		
		filmes = new ArrayList<Filme>();
		Long codigo = null;
		/*pesquisa no banco de dados a descrição passada por parametro.
		 * Se for encontrada alguma informação, retorna o código relacionado
		 * no banco de dados.
		 */
		if(rotuloConsultar == RotuloConsultar.ARTISTA){
			codigo = pesquisarArtista(textFieldPesquisa.getText());
			if(codigo != null)
				filmes = new FilmeDao().listarFilmesComArtista(codigo);
		}else{ if(rotuloConsultar == RotuloConsultar.AUTOR){
				codigo = pesquisarAutor(textFieldPesquisa.getText());
				if(codigo != null)
					filmes = new FilmeDao().listarFilmesComAutor(codigo);
			}else{ if(rotuloConsultar == RotuloConsultar.DIRETOR){
					codigo = pesquisarDiretor(textFieldPesquisa.getText());
					if(codigo != null)
						filmes = new FilmeDao().listarFilmesComDiretor(codigo);
				}else{ if(rotuloConsultar == RotuloConsultar.GENERO){
						codigo = pesquisarGenero(textFieldPesquisa.getText());
						if(codigo != null)
							filmes = new FilmeDao().listarFilmesComGenero(codigo);
					}else{
						pesquisarFilme(textFieldPesquisa.getText());
					}
				}
			}
		}
		
		/*Se o ArrayList de Filmes estiver vazio exibe no painel a mensagem 
		 * de não encontrado.
		 */
		if(filmes.isEmpty()){
			labelErro.setText(rotuloConsultar.getMsgNaoEncontrado());
			painelFilmes.setLayout(new BorderLayout());
			painelFilmes.add(labelErro, BorderLayout.CENTER);
			contentPanel.updateUI();
			return;
		}else
			criarPainelExibirFilmes();
		
	}
	
	
	private Long pesquisarArtista(String nome) {
		
		List<Artista> artistas = new ArtistaDao().listarArtistas();
		
		//Pesquisa o artista no banco de dados, e obtem o codigo caso encontre.
		Long codigoArtista = null;
		for(Artista artista : artistas){
			if(artista.getNome().toLowerCase().contains(nome.toLowerCase().subSequence(0, nome.length()))){
				codigoArtista = artista.getCodigoArtista();
				break;
			}
		}
		
		return codigoArtista;
		
	}
	
	private Long pesquisarAutor(String nome){
		List<Autor> autores = new AutorDao().listarAutores();
		
		//Pesquisa o autor no banco de dados e obtem o codigo caso encontre.
		Long codigoAutor = null;
		for(Autor autor : autores){
			if(autor.getNome().toLowerCase().contains(nome.toLowerCase().subSequence(0, nome.length()))){
				codigoAutor = autor.getCodigoAutor();
				break;
			}
		}
		
		return codigoAutor;
	}
	
	private Long pesquisarDiretor(String nome){
		List<Diretor> diretores = new DiretorDao().listarDiretores();
		
		//Pesquisa o diretor no banco de dados e obtem o codigo caso encontre.
		Long codigoDiretor = null;
		for(Diretor diretor : diretores){
			if(diretor.getNome().toLowerCase().contains(nome.toLowerCase().subSequence(0, nome.length()))){
				codigoDiretor = diretor.getCodigoDiretor();
				break;
			}
		}
		
		return codigoDiretor;
		
	}
	
	private Long pesquisarGenero(String descricao){
		List<Genero> generos = new GeneroDao().listarGeneros();
		
		//Pesquisa o genero no banco de dados e obtem o codigo caso encontre.
		Long codigoGenero = null;
		for(Genero genero : generos){
			if(genero.getDescricao().toLowerCase().contains(descricao.toLowerCase().subSequence(0, descricao.length()))){
				codigoGenero = genero.getCodigoGenero();
				break;
			}
		}
		
		return codigoGenero;
		
	}
	
	private int pesquisarFilme(String titulo){
		List<Filme> filmes = new FilmeDao().listarFilmes();
		
		this.filmes = new ArrayList<Filme>();
		
		//Pesquisa o filme no banco de dados e obtem o codigo caso encontre.
		for(Filme filme : filmes){
				if(filme.getTitulo().toLowerCase().contains(titulo.toLowerCase().subSequence(0, titulo.length()))){
					this.filmes.add(filme);
				}
		}
		if(this.filmes == null)
			return 0;
		else
			return 1;
		
	}
	
	private void criarPainelExibirFilmes(){
		
		painelFilmes.setPreferredSize(new Dimension(740, 225*filmes.size() ));
		painelFilmes.setLayout(new GridLayout(filmes.size(),1));
	
		//Cria paineis que contém informações de cada filme do Array de filmes. 
		for(Filme filme : filmes){
			
			PainelDeFilme painelDeFilme = new PainelDeFilme(filme,true);
			
			//Exibe o filme correspondente ao painel clicado.
			painelDeFilme.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					exibirFilme(filme);
				}
			});
			
			painelFilmes.add(painelDeFilme);
			
		}
		
		contentPanel.updateUI();
		return;
	}//criarPainelExibirFilmes()
	
	private void exibirFilme(Filme filme){
		new IgInfoFilme(filme,janelaReferencia);
	}
	
}//class IgConsultar
