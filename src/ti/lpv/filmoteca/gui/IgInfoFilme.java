package ti.lpv.filmoteca.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.SwingConstants;

import ti.lpv.filmoteca.modelo.Artista;
import ti.lpv.filmoteca.modelo.Autor;
import ti.lpv.filmoteca.modelo.Diretor;
import ti.lpv.filmoteca.modelo.Filme;
import ti.lpv.filmoteca.modelo.Genero;
import ti.lpv.filmoteca.modelo.Pais;

import javax.swing.JList;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.border.EtchedBorder;
import javax.swing.UIManager;

import java.awt.GridLayout;

import javax.swing.JSplitPane;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class IgInfoFilme extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Cria a Caixa de Diálogo onde exibe todas as informações
	 * do filme, cujo código é passado por parametro.
	 */
	public IgInfoFilme(Filme filme, IgJanelaPrincipal janela) {
		setTitle("Informa\u00E7oes do Filme");
		setSize(784, 577);
		setLocationRelativeTo(janela);
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(51, 102, 153));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		PainelDeFilme painelDeFilme = new PainelDeFilme(filme, false);
		painelDeFilme.setCorFundo( painelDeFilme.corClara);
		painelDeFilme.setBounds(6, 6,766, 225);
		contentPanel.add(painelDeFilme);
		
		
		//Cria os componentes que exibe a sinopse.
		JTextArea textAreaSinopse = new JTextArea();
		textAreaSinopse.setBorder(null);
		textAreaSinopse.setForeground(Color.WHITE);
		textAreaSinopse.setFont(new Font("Eras Demi ITC", Font.BOLD, 12));
		textAreaSinopse.setEditable(false);
		textAreaSinopse.setLineWrap(true);
		textAreaSinopse.setBackground(new Color(70, 130, 180));
		
		JPanel painelSinopse = new JPanel();
		painelSinopse.setBackground(new Color(70, 130, 180));
		painelSinopse.setBounds(6, 243, 295, 262);
		painelSinopse.setBorder(new TitledBorder(null, "Sinopse", TitledBorder.LEADING, TitledBorder.TOP,new Font("Eras Demi ITC", Font.BOLD, 12), new Color(255, 255, 255)));
		contentPanel.add(painelSinopse);
		painelSinopse.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPaneSinopse = new JScrollPane(textAreaSinopse);
		scrollPaneSinopse.setBorder(null);
		textAreaSinopse.setText(filme.getSinopse());
		painelSinopse.add(scrollPaneSinopse, BorderLayout.CENTER);
		
		//Cria os componentes que exibe os artistas.
		JPanel painelArtistas = new JPanel();
		painelArtistas.setBackground(new Color(70, 130, 180));
		painelArtistas.setBounds(319, 243, 143, 262);
		painelArtistas.setBorder(new TitledBorder(null, "Artistas", TitledBorder.LEADING, TitledBorder.TOP,new Font("Eras Demi ITC", Font.BOLD, 12), new Color(255, 255, 255)));
		painelArtistas.setLayout(new BorderLayout(0, 0));
		contentPanel.add(painelArtistas);
		
		JTextArea textAreaArtistas = new JTextArea();
		textAreaArtistas.setBorder(null);
		textAreaArtistas.setForeground(Color.WHITE);
		textAreaArtistas.setFont(new Font("Eras Demi ITC", Font.BOLD, 12));
		textAreaArtistas.setText((String) null);
		textAreaArtistas.setLineWrap(true);
		textAreaArtistas.setEditable(false);
		textAreaArtistas.setBackground(new Color(70, 130, 180));
		
		for(Artista a : filme.getArtistas())
			textAreaArtistas.append(a.getNome()+"\n");
		
		JScrollPane scrollPaneArtistas = new JScrollPane(textAreaArtistas);
		scrollPaneArtistas.setBorder(null);
		painelArtistas.add(scrollPaneArtistas, BorderLayout.CENTER);
		
		//Cria os componentes que exibe os autores.
		JPanel painelAutor = new JPanel();
		painelAutor.setBackground(new Color(70, 130, 180));
		painelAutor.setBounds(474, 243, 143, 262);
		painelAutor.setBorder(new TitledBorder(null, "Autores", TitledBorder.LEADING, TitledBorder.TOP,new Font("Eras Demi ITC", Font.BOLD, 12), new Color(255, 255, 255)));
		painelAutor.setLayout(new BorderLayout(0, 0));
		contentPanel.add(painelAutor);

		JTextArea textAreaAutores = new JTextArea();
		textAreaAutores.setBorder(null);
		textAreaAutores.setForeground(Color.WHITE);
		textAreaAutores.setFont(new Font("Eras Demi ITC", Font.BOLD, 12));
		textAreaAutores.setText((String) null);
		textAreaAutores.setLineWrap(true);
		textAreaAutores.setEditable(false);
		textAreaAutores.setBackground(new Color(70, 130, 180));

		for(Autor a : filme.getAutores())
			textAreaAutores.append(a.getNome()+"\n");

		JScrollPane scrollPaneAutores = new JScrollPane(textAreaAutores);
		scrollPaneAutores.setBorder(null);
		painelAutor.add(scrollPaneAutores,BorderLayout.CENTER);
		
		//Cria os componentes que exibe os Diretores.
		JPanel painelDiretores = new JPanel();
		painelDiretores.setBackground(new Color(70, 130, 180));
		painelDiretores.setBounds(629, 243, 143, 262);
		painelDiretores.setBorder(new TitledBorder(null, "Diretores", TitledBorder.LEADING, TitledBorder.TOP,new Font("Eras Demi ITC", Font.BOLD, 12), new Color(255, 255, 255)));
		painelDiretores.setLayout(new BorderLayout(0, 0));
		contentPanel.add(painelDiretores);

		JTextArea textAreaDiretores = new JTextArea();
		textAreaDiretores.setBorder(null);
		textAreaDiretores.setForeground(Color.WHITE);
		textAreaDiretores.setFont(new Font("Eras Demi ITC", Font.BOLD, 12));
		textAreaDiretores.setText((String) null);
		textAreaDiretores.setLineWrap(true);
		textAreaDiretores.setEditable(false);
		textAreaDiretores.setBackground(new Color(70, 130, 180));

		for(Diretor d : filme.getDiretores())
			textAreaDiretores.append(d.getNome()+"\n");

		JScrollPane scrollPaneDiretores = new JScrollPane(textAreaDiretores);
		scrollPaneDiretores.setBorder(null);
		painelDiretores.add(scrollPaneDiretores,BorderLayout.CENTER);
		
		
//		Painel de Botoes
		JPanel buttonPane = new JPanel();
		buttonPane.setBackground(new Color(70, 130, 180));
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setModal(true);
		setVisible(true);
	}//Construtor IgInfoFilme(Long CodFIlme)
}
