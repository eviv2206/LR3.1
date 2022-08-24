package com.company;

import java.io.*;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Эта программа в заданной строке каждое четное слово берет в кавычки, а каждое нечетное в квадратные скобки и выводит результат.");
        System.out.println("Входной файл");
        Scanner input = new Scanner(System.in);
        String filePath = findFilePath(input);
        String line = inputData(input, filePath);
        line = deleteSymbols(line);
        String[] array = ownSplit(line);
        array = createBrackets(array);
        System.out.println("Выходной файл");
        filePath = findFilePath(input);
        input.close();
        outputData(array, filePath);
    }

    public static String inputData(Scanner input, String filePath){
        String line = null;
        boolean isIncorrect;
        try{
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            line = reader.readLine();
            reader.close();
        } catch (IOException e){
            System.err.println(e.getMessage());
        }
        if(Objects.isNull(line)) {
            System.out.println("В файле не содержится строка");
            do {
                isIncorrect = false;
                System.out.println("Введите строку");
                line = input.nextLine();
                if (line.length() == 0) {
                    isIncorrect = true;
                }
            } while (isIncorrect);
        }
        return line;
    }

    public static String findFilePath(Scanner input){
        String filePath;
        FileReader reader;
        boolean isIncorrect;
        do {
            System.out.print("Введите путь к файлу: ");
            filePath = input.nextLine();
            isIncorrect = false;
            try{
                reader = new FileReader(filePath);
            } catch (FileNotFoundException e){
                System.out.println("Файл не найден");
                isIncorrect = true;
            }
        } while (isIncorrect);
        return filePath;
    }

    public static String deleteSymbols(String str){
        char[] cArray = str.toCharArray();
        int n = cArray.length;
        String newStr;
        String temp = "";
        char[] splitValues = {',', ':', '.', ';', '?', '!'};
        for (int i = 0; i < n; i++){
            for (int j = 0; j < 6; j++){
                if (cArray[i] == splitValues[j]) {
                    cArray[i] = ' ';
                }
            }
        }
        int firstIndex = findTheIndexOfFirstChar(cArray);
        int lastIndex = findTheIndexOfLastChar(cArray);
        n = lastIndex + 1;
        int i = firstIndex;
        if (firstIndex == 0){
            temp = String.valueOf(cArray[0]);
            i++;
        }
        while (i < n){
            if (cArray[i] == ' ' && cArray[i - 1] == ' ') {
                i++;
            } else {
                temp += cArray[i];
                i++;
            }
        }
        newStr = temp;
        return newStr;
    }

    public static Integer findTheIndexOfFirstChar(char[] cArray){
        boolean flag = false;
        int index = 0;
        int n = cArray.length;
        int i = 0;
        while ( i < n && !flag){
            if (cArray[i] == ' '){
                i++;
            } else{
                index = i;
                flag = true;
            }
        }
        return index;
    }

    public static Integer findTheIndexOfLastChar(char[] cArray){
        boolean flag = false;
        int index = 0;
        int i = cArray.length - 1;
        while (i > -1 && !flag){
            if (cArray[i] == ' '){
                i--;
            } else {
                index = i;
                flag = true;
            }
        }
        return index;
    }

    public static String[] ownSplit(String str){
        int counter = 0;
        char[] cArray = str.toCharArray();
        int n = cArray.length;
        for (int i = 0; i < n; i++){
            if (cArray[i] == ' '){
                counter++;
            }
        }
        String temp = "";
        int k = 0;
        String[] strArray = new String[counter + 1];
        for (int i = 0; i < n; i++){
            if (cArray[i] == ' '){
                strArray[k] = temp;
                k++;
                temp = "";
            } else {
                temp = temp + cArray[i];
            }
            strArray[k] = temp;
        }
        return strArray;
    }

    public static String[] createBrackets(String[] array){
        int n = array.length;
        for (int i = 0; i < n; i += 2){
            array[i] = "«" + array[i] + "»";
        }
        for (int i = 1; i < n; i += 2){
            array[i] = "[" + array[i] + "]";
        }
        return array;
    }

    public static void outputData(String[] array, String filepath){
        int n = array.length;
        System.out.print("Полученная строка: ");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));
            for (int i = 0; i < n; i++){
                writer.write(array[i] + " ");
                System.out.print(array[i] + " ");
            }
            writer.close();
        } catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
}
