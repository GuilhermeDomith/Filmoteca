package ti.lpv.filmoteca.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.MaskFormatter;

import ti.lpv.filmoteca.dao.FilmeDao;
import ti.lpv.filmoteca.dao.GeneroDao;
import ti.lpv.filmoteca.modelo.Artista;
import ti.lpv.filmoteca.modelo.Autor;
import ti.lpv.filmoteca.modelo.Diretor;
import ti.lpv.filmoteca.modelo.Filme;
import ti.lpv.filmoteca.modelo.Genero;
import ti.lpv.filmoteca.modelo.Pais;
import ti.lpv.filmoteca.modelo.RotuloAdicionar;
import javax.swing.ListSelectionModel;

public class IgCadastrar extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldTitulo;
	private JTextField textFieldDuracao;
	private JList listArtistas;
	private JList listAutores;
	private JList listDiretores;
	private JList listGeneros;
	private JList listPaises;
	private JTextArea textAreaSinopse;
	private JLabel labelImagem;
	private JLabel labelErroData;
	private JLabel labelErroAno;
	private JFormattedTextField fmtTextFieldLancamento;
	private JFormattedTextField fmtTextFieldAno;
	private Filme filme;
	private JCheckBox checkBoxAcao;
	private JCheckBox checkBoxComedia;
	private JCheckBox checkBoxFiccao;
	private JCheckBox checkBoxAventura;
	private JCheckBox checkBoxRomance;
	private JCheckBox checkBoxSuspense;
	private JCheckBox checkBoxTerror;
	private List<Genero> generosPadroes;
	private JLabel labelErroPoster;
	private JLabel labelErroDuracao;
	private JLabel labelErroTitulo;
	private JLabel labelErroClassEtaria;
	private JLabel labelErroGenero;
	private JLabel labelErroPais;
	private JLabel labelErroArtistas;
	private JLabel labelErroSinopse;
	private JLabel labelErroAutores;
	private JLabel labelErroDiretores;
	private JLabel labelErroNPessoal;
	private JLabel labelErroNIMDB;
	private JLabel labelErroMidia;
	private String caminhoImagem;
	
	
	/**
	 * Cria a Caixa de Dialogo onde será possível cadastrar um novo filme. 
	 */
	public IgCadastrar(IgJanelaPrincipal janela) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(IgCadastrar.class.getResource("/imagem/botaoCadastrar2.png")));
		setSize(784, 577);
		
		setLocationRelativeTo(janela);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		contentPanel.setBackground(new Color(51, 102, 153));
		setTitle("Cadastrar Filme");
		
		filme = new Filme();
		generosPadroes = new ArrayList<Genero>();
		
		//Cria o componente TabbedPane e adiciona os componentes.
		JTabbedPane tabbedPaneElenco = new JTabbedPane(JTabbedPane.BOTTOM);
		tabbedPaneElenco.setBorder(null);
		tabbedPaneElenco.setBounds(470, 203, 304, 301);
		contentPanel.add(tabbedPaneElenco);
		
		//Cria o painel para adicionar artistas.
		JPanel painelArtistas = new JPanel();
		painelArtistas.setBackground(new Color(70, 130, 180));
		painelArtistas.setBorder(new TitledBorder(null, "Artistas", TitledBorder.LEFT, TitledBorder.TOP, null, Color.WHITE));
		painelArtistas.setLayout(null);
				
		JButton adicionarArtistaButton = new JButton("Adicionar");
		adicionarArtistaButton.setBounds(18, 64, 126, 38);
		adicionarArtistaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				adicionarRemoverArtista();
			}
		});
		painelArtistas.add(adicionarArtistaButton);
		
		//Cria o painel para adicionar autores.
		JPanel painelAutores = new JPanel();
		painelAutores.setBorder(new TitledBorder(null, "Autores", TitledBorder.LEFT, TitledBorder.TOP, null, Color.WHITE));
		painelAutores.setBackground(new Color(70, 130, 180));
		painelAutores.setLayout(null);
		
		JButton adicionarAutorButton = new JButton("Adicionar");
		adicionarAutorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				adicionarRemoverAutor();
			}
		});
		adicionarAutorButton.setBounds(18, 64, 126, 38);
		painelAutores.add(adicionarAutorButton);
		
		//Cria o painel para adicionar diretores.
		JPanel painelDiretores = new JPanel();
		painelDiretores.setBorder(new TitledBorder(null, "Diretores", TitledBorder.LEFT, TitledBorder.TOP, null, Color.WHITE));
		painelDiretores.setBackground(new Color(70, 130, 180));
		painelDiretores.setLayout(null);
		
		JButton adicionarDiretorButton = new JButton("Adicionar");
		adicionarDiretorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarRemoverDiretor();
			}
		});
		adicionarDiretorButton.setBounds(18, 64, 126, 38);
		painelDiretores.add(adicionarDiretorButton);
		
		tabbedPaneElenco.addTab("Artistas", null, painelArtistas, null);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(160, 41, 115, 189);
		painelArtistas.add(scrollPane_2);
		
		listArtistas = new JList();
		listArtistas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_2.setViewportView(listArtistas);
		
		labelErroArtistas = new JLabel("New label");
		labelErroArtistas.setVisible(false);
		labelErroArtistas.setToolTipText("Voc\u00EA deve adicionar algum artista!");
		labelErroArtistas.setIcon(new ImageIcon(IgCadastrar.class.getResource("/imagem/atencao.png")));
		labelErroArtistas.setBounds(252, 19, 25, 23);
		painelArtistas.add(labelErroArtistas);
		tabbedPaneElenco.addTab("Autores", null, painelAutores, null);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(160, 41, 115, 189);
		painelAutores.add(scrollPane_3);
		
		listAutores = new JList();
		listAutores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_3.setViewportView(listAutores);
		
		labelErroAutores = new JLabel("New label");
		labelErroAutores.setVisible(false);
		labelErroAutores.setToolTipText("Voc\u00EA deve adicionar algum autor!");
		labelErroAutores.setIcon(new ImageIcon(IgCadastrar.class.getResource("/imagem/atencao.png")));
		labelErroAutores.setBounds(252, 19, 25, 23);
		painelAutores.add(labelErroAutores);
		tabbedPaneElenco.addTab("Diretores", null, painelDiretores, null);
		
		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(160, 41, 115, 189);
		painelDiretores.add(scrollPane_4);
		
		listDiretores = new JList();
		listDiretores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_4.setViewportView(listDiretores);
		
		labelErroDiretores = new JLabel("New label");
		labelErroDiretores.setVisible(false);
		labelErroDiretores.setToolTipText("Voc\u00EA deve adicionar algum diretor!");
		labelErroDiretores.setIcon(new ImageIcon(IgCadastrar.class.getResource("/imagem/atencao.png")));
		labelErroDiretores.setBounds(252, 19, 25, 23);
		painelDiretores.add(labelErroDiretores);
		
		
	
		
//		Adiciona os JRadioButton ao ButtonGroup, cria relacionamento lógico.
		ButtonGroup radioGroup = new ButtonGroup();
		
		//Cria a máscara para a data de lançamento.
		MaskFormatter mascaraData = null;
		try {
			mascaraData = new MaskFormatter("##/##/####");
			mascaraData.setPlaceholderCharacter('_');
		} catch (Exception e) {}
		
		//Cria a máscara para o ano.
		MaskFormatter mascaraAno = null;
		try {
			mascaraAno = new MaskFormatter("####");
			mascaraAno.setPlaceholderCharacter('_');
			mascaraAno.setValueContainsLiteralCharacters(false);
		} catch (Exception e) {}
		
		Integer[] nota = {1,2,3,4,5,6,7,8,9,10};
		
		
		JPanel painelClassifEtaria = new JPanel();
		painelClassifEtaria.setBackground(new Color(70, 130, 180));
		painelClassifEtaria.setBorder(new TitledBorder(null, "Classifica\u00E7\u00E3o Et\u00E1ria", TitledBorder.LEADING, TitledBorder.TOP, null, Color.WHITE));
		painelClassifEtaria.setBounds(6, 200, 160, 213);
		contentPanel.add(painelClassifEtaria);
		painelClassifEtaria.setLayout(null);
		
		JRadioButton radioButton10Anos = new JRadioButton("10 Anos");
		radioButton10Anos.setForeground(Color.WHITE);
		radioButton10Anos.setFont(new Font("Eras Demi ITC", Font.PLAIN, 12));
		radioButton10Anos.setBounds(33, 51, 81, 24);
		painelClassifEtaria.add(radioButton10Anos);
		radioButton10Anos.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				filme.setClassificacaoEtaria("10 Anos");
			}
		});
		radioGroup.add(radioButton10Anos);
		
//		Cria os componentes RadioButton para classificação etária.
		JRadioButton radioButtonLivre = new JRadioButton("Livre");
		radioButtonLivre.setForeground(Color.WHITE);
		radioButtonLivre.setFont(new Font("Eras Demi ITC", Font.PLAIN, 12));
		radioButtonLivre.setBounds(33, 20, 53, 24);
		painelClassifEtaria.add(radioButtonLivre);
		radioButtonLivre.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				filme.setClassificacaoEtaria("Livre");
			}
		});
		radioGroup.add(radioButtonLivre);
		
		JRadioButton radioButton12Anos = new JRadioButton("12 Anos");
		radioButton12Anos.setForeground(Color.WHITE);
		radioButton12Anos.setFont(new Font("Eras Demi ITC", Font.PLAIN, 12));
		radioButton12Anos.setBounds(33, 84, 81, 24);
		painelClassifEtaria.add(radioButton12Anos);
		radioButton12Anos.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				filme.setClassificacaoEtaria("12 Anos");
			}
		});
		radioGroup.add(radioButton12Anos);
		
		JRadioButton radioButton14Anos = new JRadioButton("14 Anos");
		radioButton14Anos.setForeground(Color.WHITE);
		radioButton14Anos.setFont(new Font("Eras Demi ITC", Font.PLAIN, 12));
		radioButton14Anos.setBounds(33, 115, 81, 24);
		painelClassifEtaria.add(radioButton14Anos);
		radioButton14Anos.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				filme.setClassificacaoEtaria("14 Anos");
			}
		});
		radioGroup.add(radioButton14Anos);
		
		JRadioButton radioButton18Anos = new JRadioButton("18 Anos");
		radioButton18Anos.setForeground(Color.WHITE);
		radioButton18Anos.setFont(new Font("Eras Demi ITC", Font.PLAIN, 12));
		radioButton18Anos.setBounds(33, 176, 81, 24);
		painelClassifEtaria.add(radioButton18Anos);
		radioButton18Anos.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				filme.setClassificacaoEtaria("18 Anos");
			}
		});
		radioGroup.add(radioButton18Anos);
		
		JRadioButton radioButton16Anos = new JRadioButton("16 Anos");
		radioButton16Anos.setForeground(Color.WHITE);
		radioButton16Anos.setFont(new Font("Eras Demi ITC", Font.PLAIN, 12));
		radioButton16Anos.setBounds(33, 145, 78, 24);
		painelClassifEtaria.add(radioButton16Anos);
		radioButton16Anos.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				filme.setClassificacaoEtaria("16 Anos");
			}
		});
		radioGroup.add(radioButton16Anos);
		
		labelErroClassEtaria = new JLabel("New label");
		labelErroClassEtaria.setVisible(false);
		labelErroClassEtaria.setToolTipText("Voc\u00EA deve selecionar a classifica\u00E7\u00E3o et\u00E1ria!");
		labelErroClassEtaria.setIcon(new ImageIcon(IgCadastrar.class.getResource("/imagem/atencao.png")));
		labelErroClassEtaria.setBounds(130, 18, 25, 23);
		painelClassifEtaria.add(labelErroClassEtaria);
		
		JPanel painelClassificacoes = new JPanel();
		painelClassificacoes.setBackground(new Color(70, 130, 180));
		painelClassificacoes.setBorder(new TitledBorder(null, "Classifica\u00E7\u00F5es", TitledBorder.LEADING, TitledBorder.TOP, null, Color.WHITE));
		painelClassificacoes.setBounds(6, 411, 160, 92);
		contentPanel.add(painelClassificacoes);
		painelClassificacoes.setLayout(null);
		
		JLabel labelClassificacaoPessoal = new JLabel("Pessoal:");
		labelClassificacaoPessoal.setForeground(Color.WHITE);
		labelClassificacaoPessoal.setFont(new Font("Eras Demi ITC", Font.PLAIN, 12));
		labelClassificacaoPessoal.setBounds(20, 28, 61, 14);
		painelClassificacoes.add(labelClassificacaoPessoal);

		JComboBox comboBoxNotaPessoal = new JComboBox(nota);
		comboBoxNotaPessoal.setBounds(75, 24, 53, 23);
		painelClassificacoes.add(comboBoxNotaPessoal);
		comboBoxNotaPessoal.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				filme.setClassificacaoPessoal( nota[comboBoxNotaPessoal.getSelectedIndex()] );
			}
		});
		comboBoxNotaPessoal.setBackground(Color.WHITE);

		JLabel labelClassificacaoImdb = new JLabel("IMDB:");
		labelClassificacaoImdb.setForeground(Color.WHITE);
		labelClassificacaoImdb.setFont(new Font("Eras Demi ITC", Font.PLAIN, 12));
		labelClassificacaoImdb.setBounds(20, 59, 45, 14);
		painelClassificacoes.add(labelClassificacaoImdb);

		JComboBox comboBoxNotaIMDB = new JComboBox(nota);
		comboBoxNotaIMDB.setBounds(75, 55, 53, 23);
		painelClassificacoes.add(comboBoxNotaIMDB);
		comboBoxNotaIMDB.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				filme.setClassificacaoIMDB( nota[comboBoxNotaIMDB.getSelectedIndex()] );
			}
		});
		comboBoxNotaIMDB.setBackground(Color.WHITE);
		
		labelErroNPessoal = new JLabel("New label");
		labelErroNPessoal.setToolTipText("Voc\u00EA deve selecionar o a nota pessoal!");
		labelErroNPessoal.setVisible(false);
		labelErroNPessoal.setIcon(new ImageIcon(IgCadastrar.class.getResource("/imagem/atencao.png")));
		labelErroNPessoal.setBounds(130, 24, 23, 23);
		painelClassificacoes.add(labelErroNPessoal);
		
		labelErroNIMDB = new JLabel("New label");
		labelErroNIMDB.setToolTipText("Voc\u00EA deve selecionar a nota no IMDB!");
		labelErroNIMDB.setVisible(false);
		labelErroNIMDB.setIcon(new ImageIcon(IgCadastrar.class.getResource("/imagem/atencao.png")));
		labelErroNIMDB.setBounds(130, 56, 23, 23);
		painelClassificacoes.add(labelErroNIMDB);

		JPanel painelSinopse = new JPanel();
		painelSinopse.setBackground(new Color(70, 130, 180));
		painelSinopse.setBorder(new TitledBorder(null, "Sinopse", TitledBorder.LEADING, TitledBorder.TOP, null, Color.WHITE));
		painelSinopse.setBounds(470, 9, 304, 192);
		contentPanel.add(painelSinopse);
		painelSinopse.setLayout(null);

		//Cria o componente TextArea onde o usuario adicionar a sinopse.
		textAreaSinopse = new JTextArea();
		JScrollPane barraRolagem = new JScrollPane(textAreaSinopse);
		barraRolagem.setBounds(14, 25, 276, 153);
		painelSinopse.add(barraRolagem);
		textAreaSinopse.setLineWrap(true);
		
		labelErroSinopse = new JLabel("New label");
		labelErroSinopse.setVisible(false);
		labelErroSinopse.setToolTipText("Voc\u00EA deve fornecer a sinopse!");
		labelErroSinopse.setIcon(new ImageIcon(IgCadastrar.class.getResource("/imagem/atencao.png")));
		labelErroSinopse.setBounds(267, 5, 25, 23);
		painelSinopse.add(labelErroSinopse);

		JPanel painelInfoFilme = new JPanel();
		painelInfoFilme.setBackground(new Color(70, 130, 180));
		painelInfoFilme.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(59, 59, 59)));
		painelInfoFilme.setBounds(6, 10, 452, 189);
		contentPanel.add(painelInfoFilme);
		painelInfoFilme.setLayout(null);

		labelImagem = new JLabel("<html><center>P\u00F4ster<br>(Clique para Adicionar)<center></html>");
		labelImagem.setFont(new Font("Eras Demi ITC", Font.PLAIN, 12));
		labelImagem.setForeground(Color.WHITE);
		labelImagem.setBounds(10, 10, 115, 165);
		painelInfoFilme.add(labelImagem);
		labelImagem.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent arg0) {
				obterNomeImagem();
			}
		});
		labelImagem.setHorizontalAlignment(SwingConstants.CENTER);
		labelImagem.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));

		//		Cria os componentes JLabel e adiciona ao painel.
		JLabel labelTitulo = new JLabel("T\u00EDtulo:");
		labelTitulo.setFont(new Font("Eras Demi ITC", Font.PLAIN, 12));
		labelTitulo.setForeground(Color.WHITE);
		labelTitulo.setBounds(134, 38, 46, 14);
		painelInfoFilme.add(labelTitulo);

		JLabel labelDuracao = new JLabel("Dura\u00E7\u00E3o:");
		labelDuracao.setFont(new Font("Eras Demi ITC", Font.PLAIN, 12));
		labelDuracao.setForeground(Color.WHITE);
		labelDuracao.setBounds(134, 66, 61, 14);
		painelInfoFilme.add(labelDuracao);

		JLabel labelAno = new JLabel("Ano:");
		labelAno.setFont(new Font("Eras Demi ITC", Font.PLAIN, 12));
		labelAno.setForeground(Color.WHITE);
		labelAno.setBounds(134, 95, 46, 14);
		painelInfoFilme.add(labelAno);

		JLabel labelLancamento = new JLabel("Lan\u00E7amento:");
		labelLancamento.setFont(new Font("Eras Demi ITC", Font.PLAIN, 12));
		labelLancamento.setForeground(Color.WHITE);
		labelLancamento.setBounds(134, 124, 81, 14);
		painelInfoFilme.add(labelLancamento);

		String midiaspossiveis[] = {"DVD","Blu-Ray","Arquivo Digital"};
		
		JLabel labelMdia = new JLabel("M\u00EDdia:");
		labelMdia.setFont(new Font("Eras Demi ITC", Font.PLAIN, 12));
		labelMdia.setForeground(Color.WHITE);
		labelMdia.setBounds(134, 153, 46, 14);
		painelInfoFilme.add(labelMdia);
		//Adiciona o dvd como padrão.
		filme.setMidia("DVD");
		JComboBox comboBoxMidiaDisponivel = new JComboBox(midiaspossiveis);
		comboBoxMidiaDisponivel.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				filme.setMidia(midiaspossiveis[comboBoxMidiaDisponivel.getSelectedIndex()]);
			}
		});
		comboBoxMidiaDisponivel.setBounds(213, 148, 197, 23);
		painelInfoFilme.add(comboBoxMidiaDisponivel);
		comboBoxMidiaDisponivel.setBackground(Color.WHITE);
		fmtTextFieldLancamento = new JFormattedTextField(mascaraData);
		fmtTextFieldLancamento.setBounds(213, 119, 71, 23);
		painelInfoFilme.add(fmtTextFieldLancamento);
		fmtTextFieldAno = new JFormattedTextField(mascaraAno);
		fmtTextFieldAno.setBounds(213, 91, 43, 23);
		painelInfoFilme.add(fmtTextFieldAno);

		textFieldDuracao = new JTextField();
		textFieldDuracao.setBounds(213, 63, 43, 23);
		painelInfoFilme.add(textFieldDuracao);

		textFieldTitulo = new JTextField();
		textFieldTitulo.setBounds(213, 34, 197, 23);
		painelInfoFilme.add(textFieldTitulo);
		textFieldTitulo.setColumns(10);

		JLabel lblMin = new JLabel("Min.");
		lblMin.setForeground(new Color(255, 255, 255));
		lblMin.setBounds(259, 66, 32, 16);
		painelInfoFilme.add(lblMin);

		labelErroAno = new JLabel("");
		labelErroAno.setIcon(new ImageIcon(IgCadastrar.class.getResource("/imagem/atencao.png")));
		labelErroAno.setBounds(259, 91, 25, 23);
		painelInfoFilme.add(labelErroAno);

		labelErroData = new JLabel("");
		labelErroData.setIcon(new ImageIcon(IgCadastrar.class.getResource("/imagem/atencao.png")));
		labelErroData.setBounds(286, 120, 25, 23);
		painelInfoFilme.add(labelErroData);
		labelErroData.setToolTipText("Data com formato inv\u00E1lido");
		
		labelErroTitulo = new JLabel("New label");
		labelErroTitulo.setVisible(false);
		labelErroTitulo.setToolTipText("Voc\u00EA deve fornecer o t\u00EDtulo!");
		labelErroTitulo.setIcon(new ImageIcon(IgCadastrar.class.getResource("/imagem/atencao.png")));
		labelErroTitulo.setBounds(411, 34, 25, 23);
		painelInfoFilme.add(labelErroTitulo);
		
		labelErroDuracao = new JLabel("New label");
		labelErroDuracao.setVisible(false);
		labelErroDuracao.setToolTipText("Voc\u00EA deve fornecer a dura\u00E7\u00E3o do filme!");
		labelErroDuracao.setIcon(new ImageIcon(IgCadastrar.class.getResource("/imagem/atencao.png")));
		labelErroDuracao.setBounds(286, 61, 25, 23);
		painelInfoFilme.add(labelErroDuracao);
		
		labelErroPoster = new JLabel("New label");
		labelErroPoster.setVisible(false);
		labelErroPoster.setToolTipText("Voc\u00EA deve adicionar um p\u00F4ster!");
		labelErroPoster.setIcon(new ImageIcon(IgCadastrar.class.getResource("/imagem/atencao.png")));
		labelErroPoster.setBounds(129, 10, 25, 23);
		painelInfoFilme.add(labelErroPoster);
		
		labelErroMidia = new JLabel("New label");
		labelErroMidia.setVisible(false);
		labelErroMidia.setToolTipText("Voc\u00EA deve selecionar o tipo de m\u00EDdia!");
		labelErroMidia.setIcon(new ImageIcon(IgCadastrar.class.getResource("/imagem/atencao.png")));
		labelErroMidia.setBounds(412, 149, 23, 23);
		painelInfoFilme.add(labelErroMidia);
																				
		JPanel painelGeneros = new JPanel();
		painelGeneros.setBackground(new Color(70, 130, 180));
		painelGeneros.setBounds(169, 200, 289, 213);
		contentPanel.add(painelGeneros);
		painelGeneros.setBorder(new TitledBorder(null, "G\u00EAneros", TitledBorder.LEADING, TitledBorder.TOP, null, Color.WHITE));
		painelGeneros.setLayout(null);

		//Cria os componentes CheckBox para Gênero do filme.
		
		checkBoxAcao = new JCheckBox("A\u00E7\u00E3o");
		checkBoxAcao.setForeground(Color.WHITE);
		checkBoxAcao.setFont(new Font("Eras Demi ITC", Font.PLAIN, 12));
		checkBoxAcao.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(checkBoxAcao.isSelected())
					adicionarGenero(3L);
				else
					removerGenero(3L);
				
				atualizarJListGenero();
			}
		});
		checkBoxAcao.setBounds(18, 17, 68, 24);
		painelGeneros.add(checkBoxAcao);

		checkBoxComedia = new JCheckBox("Com\u00E9dia");
		checkBoxComedia.setForeground(Color.WHITE);
		checkBoxComedia.setFont(new Font("Eras Demi ITC", Font.PLAIN, 12));
		checkBoxComedia.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(checkBoxComedia.isSelected())
					adicionarGenero(2L);
				else
					removerGenero(2L);
				
				atualizarJListGenero();
			}
		});
		checkBoxComedia.setBounds(18, 44, 112, 24);
		painelGeneros.add(checkBoxComedia);

		checkBoxFiccao = new JCheckBox("Fic\u00E7\u00E3o Cient\u00EDfica");
		checkBoxFiccao.setForeground(Color.WHITE);
		checkBoxFiccao.setFont(new Font("Eras Demi ITC", Font.PLAIN, 12));
		checkBoxFiccao.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(checkBoxFiccao.isSelected())
					adicionarGenero(4L);
				else
					removerGenero(4L);
				
				atualizarJListGenero();
			}
		});
		checkBoxFiccao.setBounds(18, 70, 125, 24);
		painelGeneros.add(checkBoxFiccao);

		checkBoxAventura = new JCheckBox("Aventura");
		checkBoxAventura.setForeground(Color.WHITE);
		checkBoxAventura.setFont(new Font("Eras Demi ITC", Font.PLAIN, 12));
		checkBoxAventura.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(checkBoxAventura.isSelected())
					adicionarGenero(1L);
				else
					removerGenero(1L);
				
				atualizarJListGenero();
			}
		});
		checkBoxAventura.setBounds(18, 98, 112, 24);
		painelGeneros.add(checkBoxAventura);

		checkBoxTerror = new JCheckBox("Terror");
		checkBoxTerror.setForeground(Color.WHITE);
		checkBoxTerror.setFont(new Font("Eras Demi ITC", Font.PLAIN, 12));
		checkBoxTerror.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(checkBoxTerror.isSelected())
					adicionarGenero(7L);
				else
					removerGenero(7L);
				
				atualizarJListGenero();
			}
		});
		checkBoxTerror.setBounds(18, 178, 96, 24);
		painelGeneros.add(checkBoxTerror);

		checkBoxSuspense = new JCheckBox("Suspense");
		checkBoxSuspense.setForeground(Color.WHITE);
		checkBoxSuspense.setFont(new Font("Eras Demi ITC", Font.PLAIN, 12));
		checkBoxSuspense.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(checkBoxSuspense.isSelected())
					adicionarGenero(6L);
				else
					removerGenero(6L);
				
				atualizarJListGenero();
			}
		});
		checkBoxSuspense.setBounds(18, 152, 96, 24);
		painelGeneros.add(checkBoxSuspense);

		checkBoxRomance = new JCheckBox("Romance");
		checkBoxRomance.setForeground(Color.WHITE);
		checkBoxRomance.setFont(new Font("Eras Demi ITC", Font.PLAIN, 12));
		checkBoxRomance.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(checkBoxRomance.isSelected())
					adicionarGenero(5L);
				else
					removerGenero(5L);
				
				atualizarJListGenero();
			}
		});
		checkBoxRomance.setBounds(18, 125, 96, 24);
		painelGeneros.add(checkBoxRomance);

		JButton buttonAdicionarGenero = new JButton("Outro");
		buttonAdicionarGenero.setBounds(152, 164, 126, 38);
		painelGeneros.add(buttonAdicionarGenero);
		
		listGeneros = new JList();
		listGeneros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(155, 17, 118, 139);
		scrollPane.setViewportView(listGeneros);

		painelGeneros.add(scrollPane);
		
		labelErroGenero = new JLabel("New label");
		labelErroGenero.setVisible(false);
		labelErroGenero.setToolTipText("Voc\u00EA deve adicionar algum genero!");
		labelErroGenero.setIcon(new ImageIcon(IgCadastrar.class.getResource("/imagem/atencao.png")));
		labelErroGenero.setBounds(135, 18, 25, 23);
		painelGeneros.add(labelErroGenero);

		//Cria o painel de Países e adiciona os componentes.
		JPanel painelPaises = new JPanel();
		painelPaises.setBackground(new Color(70, 130, 180));
		painelPaises.setBorder(new TitledBorder(null, "Pa\u00EDs", TitledBorder.LEADING, TitledBorder.TOP, null, Color.WHITE));
		painelPaises.setBounds(169, 411, 289, 90);
		contentPanel.add(painelPaises);
		painelPaises.setLayout(null);

		JButton buttonAdicionarPais = new JButton("Adicionar");
		buttonAdicionarPais.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				adicionarRemoverPais();
			}
		});
		buttonAdicionarPais.setBounds(10, 26, 126, 38);
		painelPaises.add(buttonAdicionarPais);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(155, 6, 118, 75);
		painelPaises.add(scrollPane_1);

		listPaises = new JList();
		listPaises.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_1.setViewportView(listPaises);
		
		labelErroPais = new JLabel("New label");
		labelErroPais.setVisible(false);
		labelErroPais.setToolTipText("Voc\u00EA deve adicionar algum pa\u00EDs!");
		labelErroPais.setIcon(new ImageIcon(IgCadastrar.class.getResource("/imagem/atencao.png")));
		labelErroPais.setBounds(135, 12, 25, 23);
		painelPaises.add(labelErroPais);
		buttonAdicionarGenero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarRemoverGenero();
			}
		});
		
		labelErroData.setVisible(false);
		labelErroAno.setVisible(false);
		textFieldDuracao.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent event) {
				validarDuracao(event);
			}
		});
		
		/*Caso alguma tecla seja inserida no campo, remove o icone que informa
		 * algum erro no ano fornecido, indicando que já está sendo alterada.
		 */
		fmtTextFieldAno.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				labelErroAno.setVisible(false);
			}
		});
		
		/*Caso alguma tecla seja inserida no campo, remove o JLabel que informa
		 * algum erro na data fornecida, indicando que já está sendo alterada.
		 */
		fmtTextFieldLancamento.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				labelErroData.setVisible(false);
			}
		});

		//Cria o JPanel que agrupa os botões.
		JPanel panelPainelDeBotoes = new JPanel();
		panelPainelDeBotoes.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelPainelDeBotoes.setLayout(new FlowLayout(FlowLayout.RIGHT));
		panelPainelDeBotoes.setBackground(new Color(70, 130, 180));
		getContentPane().add(panelPainelDeBotoes, BorderLayout.SOUTH);

		//Cria os componentes JButton.
		JButton buttonSalvar = new JButton("Salvar");
		buttonSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(validarCampos()){
					salvarFilme();
				}
			}
		});
		buttonSalvar.setActionCommand("OK");
		panelPainelDeBotoes.add(buttonSalvar);
		getRootPane().setDefaultButton(buttonSalvar);


		JButton buttonCancelar = new JButton("Cancelar");
		buttonCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose(); 
			}
		});
		buttonCancelar.setActionCommand("Cancel");
		panelPainelDeBotoes.add(buttonCancelar);
		
		setResizable(false);
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	/**
	 * Adiciona o genero correspondente ao codigo passado por parametro.
	 */
	private void adicionarGenero(Long codigo) {
		List<Genero> generosBD = new GeneroDao().listarGeneros();
		
		for(Genero g : generosBD)
			if(g.getCodigoGenero() == codigo)
				generosPadroes.add(g);
	}
	
	/**
	 * Remove o genero correspondente ao codigo passado por parametro.
	 */
	private void removerGenero(Long codigoGenero) {
		for(int i = 0; i < generosPadroes.size(); i ++){
			Genero g = generosPadroes.get(i);
			if(g.getCodigoGenero() == codigoGenero){
				generosPadroes.remove(i);
				break;
			}
		}
				
	}

	/**
	 * Obtem o nome da imagem, junto do caminho de onde está armazenada,
	 *  através do método exibirDlgAbrirImagem().
	 */
	private void obterNomeImagem(){
		
		filme.setPoster( exibirDlgAbrirImagem() );
		
		if(filme.getPoster() == null) return;
		
		mostrarImagem();
		
	}
	
	/**
	 * Cria e exibe a caixa de diálogo que possibilita navegar pelos arquivos 
	 * e obter a imagem que for selecionada e obter o caminho onde está salva. 
	 */
	private File exibirDlgAbrirImagem(){
		
		JFileChooser fileChooser = new JFileChooser();
		
		fileChooser.setDialogTitle("Abrir Imagem");
		
		//Cria um filtro de imagem para ser adicionado ao fileChooser.
		FileNameExtensionFilter filtro = new FileNameExtensionFilter("Imagens (*.png; *.jpg; *.jpeg)", "jpg", "png", "jpeg");
		fileChooser.setFileFilter(filtro);
		
		
		int opcao = fileChooser.showOpenDialog(this);
		
		File file = null;
		if(opcao == JFileChooser.APPROVE_OPTION){
			file = fileChooser.getSelectedFile();
			
			caminhoImagem = file.getAbsolutePath();
		}
		
		return file;
		
	}
	
	/**
	 * Exibe no painel uma pré-visualização da imagem selecionada. 
	 * 
	 */
	private void mostrarImagem(){
		Image imagem = null;
		
		try{
			imagem = ImageIO.read(filme.getPoster());
			labelImagem.setIcon(new ImageIcon( imagem.getScaledInstance(labelImagem.getWidth(), 
												labelImagem.getHeight(), Image.SCALE_DEFAULT) ));
		}catch(Exception e){
			
			System.out.println("Erro ao exibir pôster.");
		}
		
	}//construtor IgCadastrar();
	
	/**
	 * Verifica se a data de lancamento fornecida é válida. Se for inválida ou
	 * maior que atual, no momento de execução, exibe um JLabel no painel que informa ao 
	 * usuário do erro e retorna false. Se não retorna true.
	 */
	private boolean validarDataLancamento(){
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		/*Não permite que uma data inválida fornecida seja alterada para uma válida
		 * mais próxima quando for convertida para date. Causando uma exceção.
		 */
		dateFormat.setLenient(false);
		
		/*Captura a exceção de data inválida, caso seja, exibe um JLabel no 
		 * Painel informando o usuário do erro.
		 */
		try {
			Date dataDate = dateFormat.parse(fmtTextFieldLancamento.getText());
			filme.getDataLancamento().setTime(dataDate);
			
		} catch (Exception e) {
			labelErroData.setToolTipText("Data inválida!");
			labelErroData.setVisible(true);
			return false;
		}
		
		/*Verrifica se a data é maior que a data do momento de execução. Se sim, exibe um 
		 * JLabel no Painel informando o usuário do erro.
		 */
		if(filme.getDataLancamento().compareTo(Calendar.getInstance()) == 1){
			labelErroData.setToolTipText("A data fornecida é maior que a atual! ");
			labelErroData.setVisible(true);
			return false;
		}
		
		return true;
	}
	
	/**
	 * Verifica se o ano fornecido é maior que o atual, no momento de execução. 
	 * Se sim exibe um JLabel no painel que informa ao usuário do erro e 
	 * retorna false. Se não retorna true.
	 */
	private boolean validarAno(){
		
		Calendar anoCalendar = Calendar.getInstance();
		int ano;
				
		try{
			ano = Integer.parseInt(fmtTextFieldAno.getText());
		}catch(Exception e){
			labelErroAno.setToolTipText("Você deve fornecer o ano!");
			labelErroAno.setVisible(true);
			return false;
		}
		
		anoCalendar.set(Calendar.YEAR, ano);
		
		
		if(anoCalendar.compareTo(Calendar.getInstance()) == 1){
			labelErroAno.setToolTipText("O ano fornecido é maior que o atual!");
			labelErroAno.setVisible(true);
			return false;
		}
		
		filme.setAno(fmtTextFieldAno.getText());
		return true;
	}
	
	/**
	 * Verifica se o evento de teclado passado por parametro é um caracter
	 * numérico ou não. Ser sim, retorna true. Se não, retorna false.	
	 */
	private boolean eventoTecladoNumerico(KeyEvent event){
		String caracteresNumericos = "0123456789";
		
		if(caracteresNumericos.contains(event.getKeyChar()+""))
			return true;
		
		return false;
	}
	
	/**
	 * Valida o campo duração a cada evento de teclado, passado por parâmetro, verificando
	 * o tamanho, para que não exceda à um limite proposto, e permita inserir caracteres
	 * somente numéricos. Caso não seja, desfaz do evento de teclado. 
	 */
	private void validarDuracao(KeyEvent event){
		
		//Caso não seja numérico remove o evento.
		if(!eventoTecladoNumerico(event))
			event.consume();
		
		if(textFieldDuracao.getText().length() == 4)
			event.consume();
		
		return;
	}
	
	/**
	 * Verifica se todos os campos foram prenchidos, se não exibe o ícone correspondente
	 * que informa ao usuário.
	 */
	private boolean validarCampos(){
		boolean ano = validarAno(),
				data = validarDataLancamento(),
				campoValido = true;
		
		
		if(!ano || !data) campoValido = false;
		
		if(textFieldTitulo.getText().isEmpty()){
			labelErroTitulo.setVisible(true);
			campoValido = false;
		}else{
			filme.setTitulo(textFieldTitulo.getText());
		}
		
		if(textFieldDuracao.getText().isEmpty()){
			labelErroDuracao.setVisible(true);
			campoValido = false;
		}else{
			filme.setDuracao( Integer.parseInt(textFieldDuracao.getText() ));
		}
		
		if(textAreaSinopse.getText().isEmpty()){
			labelErroSinopse.setVisible(true);
			campoValido = false;
		}else{
			filme.setSinopse( textAreaSinopse.getText());
		}
		
		if(filme.getPaises().isEmpty()){
			labelErroPais.setVisible(true);
			campoValido = false;
		}
		
		if(filme.getMidia() == null){
			labelErroMidia.setVisible(true);
			campoValido = false;
		}
		
		if(filme.getArtistas().isEmpty()){
			labelErroArtistas.setVisible(true);
			campoValido = false;
		}
		
		if(filme.getAutores().isEmpty()){
			labelErroAutores.setVisible(true);
			campoValido = false;
		}
		
		if(filme.getDiretores().isEmpty()){
			labelErroDiretores.setVisible(true);
			campoValido = false;
		}
		
		if(filme.getClassificacaoEtaria() == null){
			labelErroClassEtaria.setVisible(true);
			campoValido = false;
		}
		
		if(filme.getClassificacaoIMDB() == null){
			labelErroNIMDB.setVisible(true);
			campoValido = false;
		}
		
		if(filme.getClassificacaoPessoal() == null){
			labelErroNPessoal.setVisible(true);
			campoValido = false;
		}
		
		if(filme.getPoster() == null){
			labelErroPoster.setVisible(true);
			campoValido = false;
		}
		
		if(filme.getGeneros().isEmpty() && generosPadroes.isEmpty()){
			labelErroGenero.setVisible(true);
			campoValido = false;
		}
		
		/*Se todos os campos forem válidos adiciona os generos padrões aos
		 * generos do filme antes de salvar.
		 */
		if(campoValido)
			filme.getGeneros().addAll(generosPadroes);
		else
			JOptionPane.showMessageDialog(this, "Existem campos que não foram preenchidos!", 
					"Cadastrar Filme", JOptionPane.INFORMATION_MESSAGE);
		
		return campoValido;
	}
	
	private void salvarFilme(){
		new FilmeDao().adicionarFilme(filme);
		JOptionPane.showMessageDialog(this, "Filme cadastrado com sucesso!", 
				"Cadastrar Filme", JOptionPane.INFORMATION_MESSAGE);
		dispose();
	}
	
	private void atualizarJListGenero(){
		
		List<String> generosString = new ArrayList<String>();
		for(Genero g : filme.getGeneros())
			generosString.add(g.getDescricao());
		
		listGeneros.setListData(generosString.toArray());
	}
	
	public void adicionarRemoverArtista() {
		IgAdicionar igAdicionar = new IgAdicionar(this, RotuloAdicionar.ARTISTA);
		
		filme.setArtistas( igAdicionar.getArtistasSelecionados() );
		this.listArtistas.setListData(igAdicionar.getDadosSelecionadosString().toArray());
		
	}
	
	public void adicionarRemoverAutor() {
		IgAdicionar igAdicionar = new IgAdicionar(this, RotuloAdicionar.AUTOR);
		
		filme.setAutores( igAdicionar.getAutoresSelecionados() );
		this.listAutores.setListData(igAdicionar.getDadosSelecionadosString().toArray());
		
	}
	
	public void adicionarRemoverDiretor() {
		IgAdicionar igAdicionar = new IgAdicionar(this, RotuloAdicionar.DIRETOR);
		
		filme.setDiretores( igAdicionar.getDiretoresSelecionados() );
		this.listDiretores.setListData(igAdicionar.getDadosSelecionadosString().toArray());
		
	}
	
	public void adicionarRemoverGenero() {
		IgAdicionar igAdicionar = new IgAdicionar(this, RotuloAdicionar.GENERO);
		
		filme.setGeneros( igAdicionar.getGenerosSelecionados() );
		this.listGeneros.setListData(igAdicionar.getDadosSelecionadosString().toArray());
		
	}
	
	public void adicionarRemoverPais(){
		IgAdicionar igAdicionar = new IgAdicionar(this, RotuloAdicionar.PAIS);
		
		filme.setPaises( igAdicionar.getPaisesSelecionados() );
		this.listPaises.setListData( igAdicionar.getDadosSelecionadosString().toArray());
	}
	

	public List<Artista> getArtistas() {
		return filme.getArtistas();
	}

	public List<Genero> getGeneros() {
		return filme.getGeneros();
	}

	public List<Autor> getAutores() {
		return filme.getAutores();
	}

	public List<Diretor> getDiretores() {
		return filme.getDiretores();
	}

	public List<Pais> getPaises() {
		return filme.getPaises();
	}
}//class IgCadastrar


