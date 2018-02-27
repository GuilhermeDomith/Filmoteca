package ti.lpv.filmoteca.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LookAndFeel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import ti.lpv.bd.SGBD;
import ti.lpv.filmoteca.dao.FilmeDao;
import ti.lpv.filmoteca.modelo.Filme;
import ti.lpv.filmoteca.modelo.RotuloConsultar;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class IgJanelaPrincipal extends JFrame {

	private JPanel contentPane;
	private JPanel painelFilmes;
	private List<Filme> filmes;
	private String colunaListar;
	private JLabel labelFilmesListados;

	/**
	 * Create the frame.
	 */
	public IgJanelaPrincipal() {
		setTitle("Filmoteca");
		
		alterarLookAndFeel();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1016, 577);
		
		//Cria um menu bar e adiciona os componentes.
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(70, 130, 180));
		setJMenuBar(menuBar);
		
		JMenu mnAbrir = new JMenu("Novo");
		menuBar.add(mnAbrir);
		
		JMenuItem mntmFilme = new JMenuItem("Filme");
		mntmFilme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new IgCadastrar(IgJanelaPrincipal.this);
			}
		});
		mnAbrir.add(mntmFilme);
		
		JMenu mnPesquisar = new JMenu("Listar");
		menuBar.add(mnPesquisar);
		
		JMenuItem mntmMelhoresFilmes = new JMenuItem("Melhores Filmes");
		mntmMelhoresFilmes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				labelFilmesListados.setText("MELHORES FILMES");
				colunaListar = "classificacaopessoal"; 
				listarFilmes("desc");
			}
		});
		mnPesquisar.add(mntmMelhoresFilmes);
		
		JMenu mnFilmes = new JMenu("Filmes por...");
		mnPesquisar.add(mnFilmes);
		
		JMenuItem mntmDataDeLanamento = new JMenuItem("Data de Lan\u00E7amento");
		mntmDataDeLanamento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				labelFilmesListados.setText("FILMES LISTADOS POR DATA DE LANÇAMENTO");
				colunaListar = "datalancamento";
				listarFilmes("desc");
			}
		});
		mnFilmes.add(mntmDataDeLanamento);
		
		JMenuItem mntmDurao = new JMenuItem("Dura\u00E7\u00E3o");
		mntmDurao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				labelFilmesListados.setText("FILMES LISTADOS POR DURAÇÃO");
				colunaListar = "duracao";
				listarFilmes("desc");
			}
		});
		mnFilmes.add(mntmDurao);
		
		JMenuItem mntmPorClassificaoEtria = new JMenuItem("Classifica\u00E7\u00E3o Et\u00E1ria");
		mntmPorClassificaoEtria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				labelFilmesListados.setText("FILMES LISTADOS POR CLASSIFICAÇÃO ETÁRIA");
				colunaListar = "classificacaoetaria";
				listarFilmes("desc");
			}
		});
		mnFilmes.add(mntmPorClassificaoEtria);
		
		JMenuItem mntmPorClassificaoDo = new JMenuItem("Classifica\u00E7\u00E3o do IMDB");
		mntmPorClassificaoDo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				labelFilmesListados.setText("FILMES LISTADOS POR CLASSIFICAÇÃO DO IMDB");
				colunaListar = "classificacaoimdb";
				listarFilmes("desc");
			}
		});
		mnFilmes.add(mntmPorClassificaoDo);
		
		JMenuItem mntmPorOrdemAlfabtica = new JMenuItem("Ordem Alfab\u00E9tica");
		mntmPorOrdemAlfabtica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				labelFilmesListados.setText("FILMES LISTADOS POR ORDEM ALFABÉTICA");
				colunaListar = "titulo";
				listarFilmes("asc");
			}
		});
		mnFilmes.add(mntmPorOrdemAlfabtica);
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(70, 130, 180));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
//		cria o painel que exibe os melhores filmes
		JPanel painelMelhoresFilmes = new JPanel();
		painelMelhoresFilmes.setBackground(new Color(70, 130, 180));
		contentPane.add(painelMelhoresFilmes, BorderLayout.CENTER);
		painelMelhoresFilmes.setLayout(null);
		
		labelFilmesListados = new JLabel("MELHORES FILMES");
		labelFilmesListados.setForeground(new Color(255, 255, 255));
		labelFilmesListados.setFont(new Font("Eras Bold ITC", Font.PLAIN, 22));
		labelFilmesListados.setHorizontalAlignment(SwingConstants.CENTER);
		labelFilmesListados.setBounds(16, 17, 766, 36);
		painelMelhoresFilmes.add(labelFilmesListados);
		
		painelFilmes = new JPanel();
		painelFilmes.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(0, 0, 128), new Color(0, 0, 205), new Color(0, 0, 128), new Color(0, 0, 205)));
		painelFilmes.setBackground(new Color(51, 102, 153));
		
		JScrollPane scrollPane = new JScrollPane(painelFilmes);
		scrollPane.setBounds(8, 73, 776, 427);
		painelMelhoresFilmes.add(scrollPane);
		
		//Lista os melhores filmes como padrão ao abrir a aplicação.
		colunaListar = "classificacaopessoal";
		listarFilmes("desc");
		
		// Cria o painel que exibe o menu de opçoes.
		JPanel painelMenu = new JPanel();
		painelMenu.setBackground(new Color(51, 102, 153));
		painelMenu.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		painelMenu.setPreferredSize(new Dimension(200, contentPane.getHeight()));
		contentPane.add(painelMenu, BorderLayout.WEST);
		painelMenu.setLayout(null);
		
		JButton buttonCadastrar = new JButton("Novo Filme");
		buttonCadastrar.setHorizontalAlignment(SwingConstants.LEFT);
		buttonCadastrar.setIcon(new ImageIcon(IgJanelaPrincipal.class.getResource("/imagem/botaoCadastrar2.png")));
		buttonCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new IgCadastrar(IgJanelaPrincipal.this);
			}
		});
		buttonCadastrar.setForeground(new Color(25, 25, 112));
		buttonCadastrar.setFont(new Font("Eras Demi ITC", Font.PLAIN, 14));
		buttonCadastrar.setBackground(SystemColor.activeCaption);
		buttonCadastrar.setBounds(13, 11, 174, 63);
		painelMenu.add(buttonCadastrar);
		
		JButton buttonPesquisarArtistas = new JButton("Pesquisar Artista");
		buttonPesquisarArtistas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new IgConsultar(IgJanelaPrincipal.this,RotuloConsultar.ARTISTA);
				
			}
		});
		buttonPesquisarArtistas.setForeground(new Color(25, 25, 112));
		buttonPesquisarArtistas.setFont(new Font("Eras Demi ITC", Font.PLAIN, 14));
		buttonPesquisarArtistas.setBackground(SystemColor.activeCaption);
		buttonPesquisarArtistas.setBounds(13, 184, 174, 63);
		painelMenu.add(buttonPesquisarArtistas);
		
		JButton btnPesquisarFilme = new JButton("Pesquisar Filme");
		btnPesquisarFilme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new IgConsultar(IgJanelaPrincipal.this,RotuloConsultar.FILME);
			}
		});
		btnPesquisarFilme.setForeground(new Color(25, 25, 112));
		btnPesquisarFilme.setFont(new Font("Eras Demi ITC", Font.PLAIN, 14));
		btnPesquisarFilme.setBackground(SystemColor.activeCaption);
		btnPesquisarFilme.setBounds(13, 96, 174, 63);
		painelMenu.add(btnPesquisarFilme);
		
		JButton buttonPesquisarDiretor = new JButton("Pesquisar Diretor");
		buttonPesquisarDiretor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new IgConsultar(IgJanelaPrincipal.this, RotuloConsultar.DIRETOR);
			}
		});
		buttonPesquisarDiretor.setForeground(new Color(25, 25, 112));
		buttonPesquisarDiretor.setFont(new Font("Eras Demi ITC", Font.PLAIN, 14));
		buttonPesquisarDiretor.setBackground(SystemColor.activeCaption);
		buttonPesquisarDiretor.setBounds(13, 355, 174, 63);
		painelMenu.add(buttonPesquisarDiretor);
		
		JButton buttonPesquisarGenero = new JButton("Pesquisar Genero");
		buttonPesquisarGenero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new IgConsultar(IgJanelaPrincipal.this, RotuloConsultar.GENERO);
			}
		});
		buttonPesquisarGenero.setForeground(new Color(25, 25, 112));
		buttonPesquisarGenero.setFont(new Font("Eras Demi ITC", Font.PLAIN, 14));
		buttonPesquisarGenero.setBackground(SystemColor.activeCaption);
		buttonPesquisarGenero.setBounds(13, 437, 174, 63);
		painelMenu.add(buttonPesquisarGenero);
		
		JButton buttonPesquisarAutor = new JButton("Pesquisar Autor");
		buttonPesquisarAutor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new IgConsultar(IgJanelaPrincipal.this, RotuloConsultar.AUTOR);
			}
		});
		buttonPesquisarAutor.setForeground(new Color(25, 25, 112));
		buttonPesquisarAutor.setFont(new Font("Eras Demi ITC", Font.PLAIN, 14));
		buttonPesquisarAutor.setBackground(SystemColor.activeCaption);
		buttonPesquisarAutor.setBounds(13, 269, 174, 63);
		painelMenu.add(buttonPesquisarAutor);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				dispose();
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				try {
					SGBD.getSgbd().fecharConexao();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		setResizable(false);
		setVisible(true);
	}
	
	/**
	 * Altera o look and feel da aplicação para Nimbus.
	 */
	private void alterarLookAndFeel(){

		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	private void atualizarPainelExibirFilmes(){
		painelFilmes.removeAll();
		
		if(filmes.isEmpty()){
			JLabel label = new JLabel("Nenhum filme encontrado!");
			label.setForeground(Color.WHITE);
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setFont(new Font("Eras Bold ITC", Font.PLAIN, 30));
			painelFilmes.setLayout(new BorderLayout());
			painelFilmes.add(label, BorderLayout.CENTER);
		}else{
			
			painelFilmes.setPreferredSize(new Dimension(740, 225*filmes.size() ));
			painelFilmes.setLayout(new GridLayout(filmes.size(),1));
		
			//Cria paineis que contém informações de cada filme do Array de filmes. 
			for(Filme filme : filmes){
				
				PainelDeFilme painelDeFilme = new PainelDeFilme(filme,true);
				painelDeFilme.setPreferredSize(new Dimension(482, 210));
				painelFilmes.add(painelDeFilme);
				
				//Exibe o filme correspondente ao painel clicado.
				painelDeFilme.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						new IgInfoFilme(filme, IgJanelaPrincipal.this);
					}
				});
				
				JPopupMenu popupMenu = new JPopupMenu();
				addPopup(painelDeFilme, popupMenu);
				
				JMenuItem ordenarDecrescente = new JMenuItem("Ordem decrescente");
				ordenarDecrescente.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						listarFilmes("desc");
					}
				});
				
				JMenuItem ordenarCrescente = new JMenuItem("Ordem crescente");
				ordenarCrescente.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						listarFilmes("asc");
					}
				});
				
				popupMenu.add(ordenarDecrescente);
				popupMenu.add(ordenarCrescente);
		}
				
		}
			
		contentPane.updateUI();
	}//criarPainelExibirFilmes()
	
	private void listarFilmes(String ordem){
		
		this.filmes = new FilmeDao().listarFilmesPor(colunaListar,ordem);
		
		atualizarPainelExibirFilmes();
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
