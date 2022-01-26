package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Main {
    public static void main(String[] args) throws IOException {
///*
//Общение программы с пользователем
// */
        System.out.println("Выберите действие, которое необходимо выполнить, и введите его номер:\n"
                + "1. Зашифровать текст из файла, применяя ключ.\n"
                + "2. Расшифровать текст из файла, применяя ключ.\n"
                + "3. Расшифровать текст из файла, применяя метод подбора ключа.\n"
                + "4. Расшифровать текст из файла, применяя метод статистического анализа.\n");

        System.out.println("Чтобы выйти из программы, введите exit.");

        String src = "/Users/nikola/Documents/IT/Education/Java/JavaRushUniversity/initialText.txt"; //текст для шифрования
        String encryptedText = "/Users/nikola/Documents/IT/Education/Java/JavaRushUniversity/encryptedText.txt"; //создаем файл для хранения зашифрованного текста
        String decryptedText = "/Users/nikola/Documents/IT/Education/Java/JavaRushUniversity/decryptedText.txt"; //создаем файл для хранения расшифрованного текста

        String alphabet = "абвгдежзийклмнопрстуфхцчшщъыьэюяАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ.,-:()«»%1234567890 "; //алфавит
        //System.out.println(alphabet.length());
        char[] charAlphabet = alphabet.toCharArray(); //алфавит, конвертированный в char

        String initialText = Files.readString(Paths.get(src));
        char[] charInitialText = initialText.toCharArray();

        String encrypted = Files.readString(Paths.get(encryptedText));
        char[] charEncrypted = encrypted.toCharArray();

        char[] resultEncrypted = new char[initialText.length()]; //массив для записи зашифрованного текста в формате char
        char[] resultDecrypted = new char[encrypted.length()]; //массив для записи расшифрованного текста в формате char

//        int key = 10; //ключ шифрования, значение которого в итоге нужно подобрать
//        int reverseKey = charsAlphabet.length - keyBrutForce; //ключ дешифрования для формулы

        while (true) {
            Scanner console = new Scanner(System.in);
            String exit = console.nextLine();
            if (exit.equals("1")) {
                //System.out.println("1");
                encrypt(charInitialText, charAlphabet, resultEncrypted, 10);
                //System.out.println(new String(resultEncrypted)); //проверка шифрования, необязательная строчка кода!!
            } else if (exit.equals("2")) {
                //System.out.println("2");
                decrypt(charEncrypted, charAlphabet, resultDecrypted, 10);
                //System.out.println(new String(resultDecrypted)); //проверка дешифрования, необязательная строчка кода!!
            } else if (exit.equals("3")) {
                //System.out.println("3");
                brutForce(charEncrypted, charAlphabet, resultDecrypted);
                //System.out.println(new String(decryptedText));
            } else if (exit.equals("4")) {
                System.out.println("4");
                //statistics();
            } else if (exit.equals("exit")) {
                return;
            }
        }
    }

    private static void encrypt(char[] charInitialText, char[] charAlphabet, char[] resultEncrypted, int key) { //продумать введение кода шифрования пользователем
        for (int i = 0; i < charInitialText.length; i++) {
            char charTempOuter = charInitialText[i];
            for (int j = 0; j < charAlphabet.length; j++) {
                char charTempInner = charAlphabet[j];
                if (charTempOuter == charTempInner) {
                    resultEncrypted[i] = charAlphabet[(j + key) % charAlphabet.length];
                }
            }
        }
    }

    private static void decrypt(char[] charEncrypted, char[] charAlphabet, char[] resultDecrypted, int key) { //продумать введение кода дешифрования пользователем
        for (int i = 0; i < charEncrypted.length; i++) {
            char charTempOuter = charEncrypted[i];
            for (int j = 0; j < charAlphabet.length; j++) {
                char charTempInner = charAlphabet[j];
                if (charTempOuter == charTempInner) {
                    resultDecrypted[i] = charAlphabet[(j + (charAlphabet.length - key)) % charAlphabet.length];
                }
            }
        }
    }

    private static void brutForce(char[] charEncrypted, char[] charAlphabet, char[] resultDecrypted) throws IOException {
        int key = 10;
//        for (key = 1; key <= charAlphabet.length; key++) {
        for (int i = 0; i < charEncrypted.length; i++) {
            char charTempOuter = charEncrypted[i];
            for (int j = 0; j < charAlphabet.length; j++) {
                char charTempInner = charAlphabet[j];
                if (charTempOuter == charTempInner) {
                    resultDecrypted[i] = charAlphabet[(j + (charAlphabet.length - key)) % charAlphabet.length];
                    //теперь нужно проверить resultDecrypted на "адекватность"
                    int resultComaSpace = isExistComaSpace(resultDecrypted);
                    int resultPointSpace = isExistPointSpace(resultDecrypted);
                    int resultInterestSpace = isExistInterestSpace(resultDecrypted);
                    int resultLongWord = isCountLongWord();
                    System.out.println(resultComaSpace);

//                        if ((resultComaSpace > 5) && (resultPointSpace > 5) && (resultInterestSpace > 1) && (resultLongWord < 5)) {
//                            System.out.println("Ключ - " + key);
//
//                        } break;
                }
            }
        }

    }

    private static int isExistComaSpace(char[] textForAnalysis) {
        int counterComaSpace = 0;
        for (int i = 0; i < textForAnalysis.length; i++) {
            if (textForAnalysis[i] == ',' && textForAnalysis[i + 1] == ' ') {
                counterComaSpace++;
            }
        } return counterComaSpace;
    }

    private static int isExistPointSpace(char[] textForAnalysis) {
        int counterPointSpace = 0;
        for (int i = 0; i < textForAnalysis.length; i++) {
            if (textForAnalysis[i] == '.' && textForAnalysis[i + 1] == ' ') {
                counterPointSpace++;
            }
        } return counterPointSpace;
    }

    private static int isExistInterestSpace(char[] textForAnalysis) {
        int counterInterest = 0;
        for (int i = 0; i < textForAnalysis.length; i++) {
            if (textForAnalysis[i] == '%' && textForAnalysis[i + 1] == ' ') {
                counterInterest++;
            }
        } return counterInterest;
    }

    private static int isCountLongWord() throws IOException {
        String encryptedText = "/Users/nikola/Documents/IT/Education/Java/JavaRushUniversity/decryptedText.txt";
        String lines = Files.readString(Paths.get(encryptedText), UTF_8);
        String[] linesWithSpace = lines.split(" ");
        List<Integer> list = new ArrayList<>();

        for (String value : linesWithSpace) {
            list.add(value.length());
        }

        int counterLongWord = 0;
        for (Integer integer : list) {
            if (integer >= 20) {
                counterLongWord++;
//                System.out.println(integer);
            }
        }
        return counterLongWord;
        //System.out.println(">20 букв - " + counter);
    }

//    private static void statistics() {
//        System.out.println("Вы выбрали 4");
//    }


}

