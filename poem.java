import java.io.*;
import java.util.regex.*;
import java.util.Scanner;
import java.util.ArrayList;
public class poem {
    public static void main(String[] args) throws Exception {
        Scanner reader = new Scanner(System.in);
        ArrayList<String> sentenceList = new ArrayList<String>();
        ArrayList<String> wordList = new ArrayList<String>();
        String[] sentences = new String[500];
        String keyWord, word1, word2, word3;
        int fileNum = (int)(Math.random() * 1000);

        // Prompt user to pick a text
        System.out.println("Please type in the name of the text you would like to use followed by .txt");
        String file = reader.nextLine();
        Scanner fileReader = new Scanner(new File(file));

        // Pick a word
        System.out.println("What word would you like to make a poem about?");
        keyWord = reader.nextLine();

        // Scan text for word put sentence into array
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
        // Pick 3 words associated with main word
        System.out.println("Please type a word that you associate with " + keyWord);
        word1 = reader.nextLine();
        System.out.println("Please type another word that you associate with " + keyWord);
        word2 = reader.nextLine();
        System.out.println("Please type a third word that you assoicate with " + keyWord);
        word3 = reader.nextLine();

        // Put associative word sentences into arrayList
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

        // Print random sentences 


        // Export poem to text file
        System.out.println("Your poem is saved as: "+ keyWord + fileNum + ".txt");
        try(Writer fout = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(keyWord + fileNum + ".txt"), "utf-8"))) {
            fout.write("\n" + keyWord.toUpperCase() + " - " + file + "\n\n");
            fout.write(sentenceList.get((int)(Math.random() * sentenceList.size()) - 1) + "\n");
            fout.write(wordList.get((int)(Math.random() * wordList.size()) - 1) + "\n");
            fout.write(sentenceList.get((int)(Math.random() * sentenceList.size()) - 1) + "\n");
            fout.write(wordList.get((int)(Math.random() * wordList.size())) + "\n");
            fout.write(sentenceList.get((int)(Math.random() * sentenceList.size()) - 1));
        } catch(IOException exe) {
            System.out.println("IOException: Program Halted");
        }

        reader.close();
        fileReader.close();
    }
    // Takes word location and returns sentence it's in
    public static boolean sentence(String s, String str) {
        s = s.toLowerCase();
        return (s.indexOf(str.toLowerCase()) != -1);
    }
}