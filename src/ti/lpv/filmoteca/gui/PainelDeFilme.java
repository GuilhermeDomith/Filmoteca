package ti.lpv.filmoteca.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import ti.lpv.filmoteca.modelo.Filme;
import ti.lpv.filmoteca.modelo.Genero;
import ti.lpv.filmoteca.modelo.Pais;

public class PainelDeFilme extends JPanel {
	private JPanel painelGeneros;
	private JPanel painelPaises;
	protected Color corEscura = new Color(51, 102, 153),
			corClara = new Color(70, 130, 180);

	/**
	 * Cria um painel com informaçoes principais do filme passado por parâmetro.
	 */
	public PainelDeFilme(Filme filme, boolean habilitarMouseListener) {
		
		setBorder(new TitledBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null)));
		setSize(755, 225);
		setLayout(null);
		
		setBackground(corEscura);
		
		/*Se habilitarMouseListener for true, altera a cor de fundo do painel de filmes, e dos paineis que ele contem, 
		 * quando o Mouse sobrepõe o painel.
		 */
		if(habilitarMouseListener){
			addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					setCorFundo(corClara);
				}
				@Override
				public void mouseExited(MouseEvent e) {
					setCorFundo(corEscura);
				}
			});
		}
		
		
		//Cria o componente que exibe o pôster.
		
//		ImageIcon poster = new ImageIcon(filme.getPoster().getAbsolutePath());
		Image img = null;
		try { img = ImageIO.read(filme.getPoster());
		} catch (IOException e) { e.printStackTrace(); }
		
		JLabel lblPoster = new JLabel(new ImageIcon(img.getScaledInstance(143, 193, Image.SCALE_DEFAULT)));
		lblPoster.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblPoster.setBounds(16, 15, 143, 193);
		add(lblPoster);
		lblPoster.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel labelTitulo = new JLabel(filme.getTitulo());
		labelTitulo.setBackground(getBackground());
		labelTitulo.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		labelTitulo.setFont(new Font("Eras Bold ITC", Font.PLAIN, 22));
		labelTitulo.setForeground(new Color(255, 255, 255));
		labelTitulo.setBounds(174, 6, 562, 42);
		add(labelTitulo);


		//Cria os componentes que exibem as informaçoes dos Generos.
		painelGeneros = new JPanel();
		painelGeneros.setBackground(getBackground());
		painelGeneros.setBorder(null);
		int quantGeneros = filme.getGeneros().size();
		if(quantGeneros > 7)
			painelGeneros.setLayout(new GridLayout(quantGeneros,1));
		else
			painelGeneros.setLayout(new GridLayout(7,1));


		JScrollPane scrollPaneGeneros = new JScrollPane(painelGeneros);
		scrollPaneGeneros.setBorder(null);
		scrollPaneGeneros.setBounds(166, 55, 120, 143);
		add(scrollPaneGeneros);

		for(Genero g : filme.getGeneros()){
			JLabel labelGenero = new JLabel(g.getDescricao());
			labelGenero.setPreferredSize(new Dimension(80, 20));
			labelGenero.setForeground(Color.WHITE);
			labelGenero.setFont(new Font("Eras Demi ITC", Font.BOLD, 12));
			painelGeneros.add(labelGenero);
		}

		//Cria os componentes que exibem as informaçoes do país.
		JLabel labelPaises = new JLabel("Pa\u00EDs:");
		labelPaises.setForeground(new Color(245, 245, 245));
		labelPaises.setFont(new Font("Eras Light ITC", Font.BOLD, 12));
		labelPaises.setBounds(337, 55, 37, 16);
		add(labelPaises);

		painelPaises = new JPanel();
		painelPaises.setBorder(null);
		painelPaises.setBackground(getBackground());
		int quantPais = filme.getPaises().size();
		if(quantPais > 3)
			painelPaises.setLayout(new GridLayout(quantPais,1));
		else
			painelPaises.setLayout(new GridLayout(3,1));

		JScrollPane scrollPanePais = new JScrollPane(painelPaises);
		scrollPanePais.setBorder(null);
		scrollPanePais.setBounds(373, 54, 110, 60);
		add(scrollPanePais);

		for(Pais p : filme.getPaises()){
			JLabel labelPais = new JLabel(p.getNome());
			labelPais.setPreferredSize(new Dimension(80, 20));
			labelPais.setForeground(Color.WHITE);
			labelPais.setFont(new Font("Eras Demi ITC", Font.BOLD, 12));
			painelPaises.add(labelPais);
		}

		//Cria os componente que exibe o ano.
		JLabel labelAno = new JLabel("Ano:");
		labelAno.setForeground(new Color(245, 245, 245));
		labelAno.setFont(new Font("Eras Light ITC", Font.BOLD, 12));
		labelAno.setBounds(339, 155, 37, 16);
		add(labelAno);

		JLabel labelValorAno = new JLabel(filme.getAno());
		labelValorAno.setForeground(Color.WHITE);
		labelValorAno.setFont(new Font("Eras Demi ITC", Font.BOLD, 12));
		labelValorAno.setBounds(373, 155, 55, 16);
		add(labelValorAno);

		//Cria os componente que exibe a data de lançamento.
		JLabel labelLancamento = new JLabel("Lan\u00E7amento: ");
		labelLancamento.setForeground(new Color(245, 245, 245));
		labelLancamento.setFont(new Font("Eras Light ITC", Font.BOLD, 12));
		labelLancamento.setBounds(293, 182, 87, 16);
		add(labelLancamento);

		int ano = filme.getDataLancamento().get(Calendar.YEAR),
				mes = filme.getDataLancamento().get(Calendar.MONTH),
				dia = filme.getDataLancamento().get(Calendar.DAY_OF_MONTH);

		JLabel labelValorLancamento = new JLabel(dia+"\\"+mes+"\\"+ano );
		labelValorLancamento.setFont(new Font("Eras Demi ITC", Font.BOLD, 12));
		labelValorLancamento.setForeground(Color.WHITE);
		labelValorLancamento.setBounds(373, 182, 78, 16);
		add(labelValorLancamento);

		//Cria os componentes que exibe a duração do filme.
		JLabel labelDuracao = new JLabel("Dura\u00E7\u00E3o:");
		labelDuracao.setForeground(new Color(245, 245, 245));
		labelDuracao.setFont(new Font("Eras Light ITC", Font.BOLD, 12));
		labelDuracao.setBounds(312, 127, 55, 16);
		add(labelDuracao);

		JLabel labelValorDuracao = new JLabel(filme.getDuracao()+" Minutos");
		labelValorDuracao.setForeground(Color.WHITE);
		labelValorDuracao.setFont(new Font("Eras Demi ITC", Font.BOLD, 12));
		labelValorDuracao.setBounds(373, 127, 108, 16);
		add(labelValorDuracao);

		//Cria os componentes que exibe a nota pessoal e nota do IMDB.
		JLabel lblNotaPessoal = new JLabel("Nota Pessoal:");
		lblNotaPessoal.setForeground(new Color(245, 245, 245));
		lblNotaPessoal.setFont(new Font("Eras Light ITC", Font.BOLD, 12));
		lblNotaPessoal.setBounds(539, 55, 87, 16);
		add(lblNotaPessoal);

		JLabel LabelValorNotaPessoal = new JLabel(filme.getClassificacaoPessoal().toString());
		LabelValorNotaPessoal.setFont(new Font("Eras Demi ITC", Font.BOLD, 12));
		LabelValorNotaPessoal.setForeground(Color.WHITE);
		LabelValorNotaPessoal.setBounds(624, 55, 55, 16);
		add(LabelValorNotaPessoal);

		JLabel lblNotaImdb = new JLabel("Nota IMDB:");
		lblNotaImdb.setForeground(new Color(245, 245, 245));
		lblNotaImdb.setFont(new Font("Eras Light ITC", Font.BOLD, 12));
		lblNotaImdb.setBounds(549, 83, 71, 16);
		add(lblNotaImdb);

		JLabel labelValorNotaIMDB = new JLabel(filme.getClassificacaoIMDB().toString());
		labelValorNotaIMDB.setFont(new Font("Eras Demi ITC", Font.BOLD, 12));
		labelValorNotaIMDB.setForeground(Color.WHITE);
		labelValorNotaIMDB.setBounds(624, 83, 55, 16);
		add(labelValorNotaIMDB);

		//Cria o componente que exibe a classificação etária.
		JLabel lblAnos = new JLabel(filme.getClassificacaoEtaria());
		lblAnos.setForeground(Color.WHITE);
		lblAnos.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		lblAnos.setHorizontalAlignment(SwingConstants.CENTER);
		lblAnos.setFont(new Font("Eras Demi ITC", Font.PLAIN, 20));
		lblAnos.setBounds(587, 167, 151, 42);
		add(lblAnos);

		//Cria os componentes que exibe a mídia disponível.
		JLabel lblMdiaDisponvel = new JLabel("M\u00EDdia Dispon\u00EDvel:");
		lblMdiaDisponvel.setForeground(new Color(245, 245, 245));
		lblMdiaDisponvel.setFont(new Font("Eras Light ITC", Font.BOLD, 12));
		lblMdiaDisponvel.setBounds(516, 127, 110, 16);
		add(lblMdiaDisponvel);

		JLabel labelValorMidia = new JLabel(filme.getMidia());
		labelValorMidia.setFont(new Font("Eras Demi ITC", Font.BOLD, 12));
		labelValorMidia.setForeground(Color.WHITE);
		labelValorMidia.setBounds(624, 127, 128, 16);
		add(labelValorMidia);
		
	}
	
	public void setCorFundo(Color bg) {
		this.setBackground(bg);
		painelGeneros.setBackground(bg);
		painelPaises.setBackground(bg);
	}
	
}
