import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.*;
import java.io.*;
import java.util.regex.*;
import java.util.Scanner;
import java.util.ArrayList;

public class PoemGenerator extends JFrame {
    private static final long serialVersionUID = 1L;
    private JButton generate;
    private JLabel header, wordPrompt, saveLoc;
    private JComboBox box;
    String[] choices = {"Agamemnon.txt", "MerchantOfVenice.txt", "TheRepublic.txt", "WeaponsOfMathDestruction.txt"};
    private String text;
    private JTextField keyWordF, word1F, word2F, word3F;
    private JTextArea poem;

    Scanner reader = new Scanner(System.in);
    ArrayList<String> sentenceList = new ArrayList<String>();
    ArrayList<String> wordList = new ArrayList<String>();
    String[] sentences = new String[500];
    String keyWord, word1, word2, word3;
    Scanner fileReader;
    String s1, s2, s3, s4, s5, title;
    int fileNum = (int)(Math.random() * 1000);

    public PoemGenerator() {
        createView();

        setTitle("Poem Generator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(true);
        
    }

    private void createView() {
        JPanel panel = new JPanel();
        getContentPane().add(panel);

        header = new JLabel("Select a text from the dropdown menu");
        panel.add(header);
        
        box = new JComboBox(choices);
        box.addActionListener((ActionListener) new boxActionListener());
        panel.add(box);

        wordPrompt = new JLabel("Please type a key word followed by 3 words that you associate with the key word:");
        panel.add(wordPrompt);

        keyWordF = new JTextField();
        keyWordF.setPreferredSize(new Dimension(200, 30));
        panel.add(keyWordF);

        word1F = new JTextField();
        word1F.setPreferredSize(new Dimension(100, 30));
        panel.add(word1F);

        word2F = new JTextField();
        word2F.setPreferredSize(new Dimension(100, 30));
        panel.add(word2F);

        word3F = new JTextField();
        word3F.setPreferredSize(new Dimension(100, 30));
        panel.add(word3F);

        generate = new JButton("Generate");
        generate.addActionListener((ActionListener) new generateActionListener());
        panel.add(generate);

        poem = new JTextArea();
        poem.setEditable(false);
        poem.setLineWrap(true);
        poem.setWrapStyleWord(true);
        poem.setPreferredSize(new Dimension(600, 350));
        JScrollPane scrollPane = new JScrollPane(poem);
        panel.add(scrollPane);

        saveLoc = new JLabel();
        panel.add(saveLoc);
    }
    public static void main(String[] args) {
        new PoemGenerator().setVisible(true);
    }

    private class generateActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try( Scanner fileReader = new Scanner(new File(text))) {

            poem.setText("");

            keyWord = keyWordF.getText();
            word1 = word1F.getText();
            word2 = word2F.getText();
            word3 = word3F.getText();

            while(fileReader.hasNextLine()) {
                sentenceList.add(fileReader.nextLine());
            }
            String[] sentenceArray = sentenceList.toArray(new String[sentenceList.size()]);
            sentenceList.clear();
            for (int r=0;r<sentenceArray.length;r++) {
               sentences = sentenceArray[r].split("(?<=[.!?])\\s*");
               for (int i=0;i<sentences.length;i++) {
                    if(sentence(sentences[i], keyWord)) {
                        sentenceList.add(sentences[i]);
                    }
                }
            }

            for (int r=0;r<sentenceArray.length;r++) {
                sentences = sentenceArray[r].split("(?<=[.!?])\\s*");
                for (int i=0;i<sentences.length;i++) {
                    if(sentence(sentences[i], word1)) {
                        wordList.add(sentences[i]);
                    } else if(sentence(sentences[i], word2)){
                        wordList.add(sentences[i]);
                    } else if(sentence(sentences[i], word3)) {
                        wordList.add(sentences[i]);
                    }
                }
            }

            title = "\n" + keyWord.toUpperCase() + " - " + text + "\n\n";
            s1 = sentenceList.get((int)(Math.random() * sentenceList.size()) - 1) + "\n";
            s2 = wordList.get((int)(Math.random() * wordList.size()) - 1) + "\n";
            s3 = sentenceList.get((int)(Math.random() * sentenceList.size()) - 1) + "\n";
            s4 = wordList.get((int)(Math.random() * wordList.size())) + "\n";
            s5 = sentenceList.get((int)(Math.random() * sentenceList.size()) - 1);
            System.out.println("Your poem is saved as: "+ keyWord + fileNum + ".txt");
            try(Writer fout = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(keyWord + fileNum + ".txt"), "utf-8"))) {
                fout.write(title);
                poem.append(title);
                fout.write(s1);
                poem.append(s1);
                fout.write(s2);
                poem.append(s2);
                fout.write(s3);
                poem.append(s3);
                fout.write(s4);
                poem.append(s4);
                fout.write(s5);
                poem.append(s5);
                saveLoc.setText("Your poem is saved as:" + keyWord + fileNum + ".txt");
            } catch(IOException exe) {
                System.out.println("IOException: Program Halted");
            }
            reader.close();
            fileReader.close();
            } catch (IOException exe) {
                System.out.print("IOException error");
            }
        }
    }

    private class boxActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            text = (String) box.getSelectedItem();
            switch(text) {
                case "MerchantOfVenice.txt":
                    text = "MerchantOfVenice.txt";
                    break;
                case "TheRepublic.txt":
                    text = "TheRepublic.txt";
                    break;
                case "WeaponsOfMathDestruction.txt":
                    text = "WeaponsOfMathDestruction.txt";
                    break;
                default:
                    text = "Agamemnon.txt";
                    break;
            }
        }
    }

    public static boolean sentence(String s, String str) {
        s = s.toLowerCase();
        return (s.indexOf(str.toLowerCase()) != -1);
    }
}