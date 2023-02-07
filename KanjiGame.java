/**
 * Ovo je Igra spajanja japanskih znakova sa njihovim engleskim znaèenjem ili sa njihovim japanskim izgovorom.
 * Cilj igre je toèan gumb pritisnut, èije znaèenje je jedako onome u labeli.
 */
package hr.vsite;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.util.*;
import javax.swing.SwingConstants;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JRadioButton;

public class KanjiGame extends JFrame {
/**
 * Inicijalizacija varijabli
 * */
	private JPanel contentPane;
	private final JLabel lblNewLabel = new JLabel("Kanji");
	private static String[] engl = new String[48];
	private static String[] kanj = new String[48];
	private static String[] kana = new String[48];
	private static byte correct = 1;
	private static byte correct_1 = 1;
	private static byte correct_2 = 1;
	private static int count = -1;
	private static int count1 = 0;
	private static byte option1 = 0;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					KanjiGame frame = new KanjiGame();
					frame.setVisible(true);
					/**
					 * Spajanje s xml datotekom i prebacivanje vrijednosti u string nizove.
					 * Ovo je važan prvi dio programa pomoæu kojeg dolazimo do baze podataka koja je dio svrhe ovog programa.
					 */
					File file = new File("grade1xml.xml");
					DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
					        .newInstance();
					DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
					Document document = documentBuilder.parse(file);
					for (int i = 0; i < 48; i++){
						kanj[i] = document.getElementsByTagName("kanji1").item(i).getTextContent();
						engl[i] = document.getElementsByTagName("english1").item(i).getTextContent();
						kana[i] = document.getElementsByTagName("kana1").item(i).getTextContent();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the frame.
	 */
	public KanjiGame() {
		setTitle("Kanji Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 840, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		/**
		 * Inicijalizacija labela
		 */
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setText("Kanji");
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setFont(new Font("MS Gothic", Font.PLAIN, 30));
		JLabel lblTruth = new JLabel("Truth");
		lblTruth.setFont(new Font("Tahoma", Font.PLAIN, 15));
		JLabel lblCount = new JLabel("Count: ");
		/**
		 * Inicijalizacija gumb-ova
		 */
		JButton btnClearCount = new JButton("Clear Count");
		JButton btnOption = new JButton("KA");
		btnOption.setFont(new Font("MS Gothic", Font.PLAIN, 20));
		JButton btnOption_1 = new JButton("KA");
		btnOption_1.setFont(new Font("MS Gothic", Font.PLAIN, 20));
		JButton btnOption_2 = new JButton("KA");
		btnOption_2.setFont(new Font("MS Gothic", Font.PLAIN, 20));
		/**
		 * Inicijalizacija radio gumb-ova i njiovi ActionListeneri koji provjeravaju koja je opcija pritisnuta.
		 */
		JRadioButton rdbtnEnglishKanji = new JRadioButton("English -> Kanji");	
		rdbtnEnglishKanji.setSelected(true);
		JRadioButton rdbtnKanaKanji = new JRadioButton("Kana -> Kanji");
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnEnglishKanji);
		group.add(rdbtnKanaKanji);
		rdbtnEnglishKanji.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				option1 = 0;
			}
		});
		rdbtnKanaKanji.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				option1 = 1;
			}
		});
		/**
		 * ActionListener-i za tri inicijalizirana gumba.
		 * Ovdije se odvija glavni dio programa.
		 * Prvo provjeravamo dali je pritisnut gumb toèan odgovor.
		 * Drugo, provjeravamo opcije radio gumb-ova, te postavljamo treženu opciju.
		 * Treæe, postavljamo random integer-e, jedan za odabir gumba nakojem æe biti toèan odgovor,
		 * drugi za odabir sljedeæeg pitanja i postavljanje toènog odgovora na veæ odabrani gumb,
		 * a treæi i èetvrti random su za postavljanje odgovora na ostala dva gumba.
		 */
		btnOption.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) { 
				switch (correct){
				case 1:	lblTruth.setText("Correct!");
						lblTruth.setForeground(Color.GREEN);
						count += 1;
						lblCount.setText("Correct: "+Integer.toString(count)+"   Wrong: "+Integer.toString(count1));
						break;
				case 0:	lblTruth.setText("Wrong!"); 
						lblTruth.setForeground(Color.RED); 
						count1 += 1;
						lblCount.setText("Correct: "+Integer.toString(count)+"   Wrong: "+Integer.toString(count1));
				break;
				} 
				Random rand = new Random();
				int rid = rand.nextInt(3);
				int red = rand.nextInt(48);
				int red1 = rand.nextInt(48);
				int red2 = rand.nextInt(48);
				switch(option1){
					case 0: lblNewLabel.setText(engl[red]); break;
					case 1: lblNewLabel.setText(kana[red]); break;
				}
				switch(rid){
					case 0: btnOption.setText(kanj[red]);
							btnOption_1.setText(kanj[red1]);
							btnOption_2.setText(kanj[red2]);
							correct = 1; correct_1 = 0; correct_2 = 0;
							break;
					case 1: btnOption_1.setText(kanj[red]);
							btnOption.setText(kanj[red1]);
							btnOption_2.setText(kanj[red2]);
							correct_1 = 1; correct = 0; correct_2 = 0;
							break;
					case 2: btnOption_2.setText(kanj[red]);
							btnOption_1.setText(kanj[red1]);
							btnOption.setText(kanj[red2]);
							correct_2 = 1; correct_1 = 0; correct = 0;
							break;
				}}});
		btnOption_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				switch (correct_1){
				case 1:	lblTruth.setText("Correct!");
						lblTruth.setForeground(Color.GREEN);
						count += 1;
						lblCount.setText("Correct: "+Integer.toString(count)+"   Wrong: "+Integer.toString(count1));
						break;
				case 0:	lblTruth.setText("Wrong!"); 
						lblTruth.setForeground(Color.RED); 
						count1 += 1;
						lblCount.setText("Correct: "+Integer.toString(count)+"   Wrong: "+Integer.toString(count1));
						break;
				} 
				Random rand = new Random();
				int rid = rand.nextInt(3);
				int red = rand.nextInt(48);
				int red1 = rand.nextInt(48);
				int red2 = rand.nextInt(48);
				switch(option1){
					case 0: lblNewLabel.setText(engl[red]); break;
					case 1: lblNewLabel.setText(kana[red]); break;
				}
				switch(rid){
					case 0: btnOption.setText(kanj[red]);
							btnOption_1.setText(kanj[red1]);
							btnOption_2.setText(kanj[red2]);
							correct = 1; correct_1 = 0; correct_2 = 0;
							break;
					case 1: btnOption_1.setText(kanj[red]);
							btnOption.setText(kanj[red1]);
							btnOption_2.setText(kanj[red2]);
							correct_1 = 1; correct = 0; correct_2 = 0;
							break;
					case 2: btnOption_2.setText(kanj[red]);
							btnOption_1.setText(kanj[red1]);
							btnOption.setText(kanj[red2]);
							correct_2 = 1; correct_1 = 0; correct = 0;
							break;
				}}});
		btnOption_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				switch (correct_2){
					case 1:	lblTruth.setText("Correct!");
							lblTruth.setForeground(Color.GREEN);
							count += 1;
							lblCount.setText("Correct: "+Integer.toString(count)+"   Wrong: "+Integer.toString(count1));
							break;
					case 0:	lblTruth.setText("Wrong!"); 
							lblTruth.setForeground(Color.RED); 
							count1 += 1;
							lblCount.setText("Correct: "+Integer.toString(count)+"   Wrong: "+Integer.toString(count1));
							break;
				} 
				Random rand = new Random();
				int rid = rand.nextInt(3);
				int red = rand.nextInt(48);
				int red1 = rand.nextInt(48);
				int red2 = rand.nextInt(48);
				switch(option1){
					case 0: lblNewLabel.setText(engl[red]); break;
					case 1: lblNewLabel.setText(kana[red]); break;
				}
				switch(rid){
					case 0: btnOption.setText(kanj[red]);
							btnOption_1.setText(kanj[red1]);
							btnOption_2.setText(kanj[red2]);
							correct = 1; correct_1 = 0; correct_2 = 0;
							break;
					case 1: btnOption_1.setText(kanj[red]);
							btnOption.setText(kanj[red1]);
							btnOption_2.setText(kanj[red2]);
							correct_1 = 1; correct = 0; correct_2 = 0;
							break;
					case 2: btnOption_2.setText(kanj[red]);
							btnOption_1.setText(kanj[red1]);
							btnOption.setText(kanj[red2]);
							correct_2 = 1; correct_1 = 0; correct = 0;
							break;
				}}});
		/**
		 * Pomoæu ovog gumba resetiramo labelu Count koja nam broji toène i netoène odgovore.
		 */
		btnClearCount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				count = 0;
				count1 = 0;
				lblCount.setText("Correct: "+Integer.toString(count)+"   Wrong: "+Integer.toString(count1));
			}
		});
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(168, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(rdbtnEnglishKanji)
								.addComponent(rdbtnKanaKanji))
							.addContainerGap())
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnOption)
							.addGap(98)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblTruth)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(btnOption_1)
									.addGap(82)
									.addComponent(btnOption_2)))
							.addGap(231))))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(23)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnClearCount)
						.addComponent(lblCount))
					.addContainerGap(428, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnOption, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnOption_1, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnOption_2, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))
					.addGap(37)
					.addComponent(lblTruth)
					.addPreferredGap(ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCount)
						.addComponent(rdbtnEnglishKanji))
					.addGap(16)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnClearCount)
						.addComponent(rdbtnKanaKanji))
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
	}
}
