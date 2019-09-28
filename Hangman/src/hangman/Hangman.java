/*
    Author: Matthew Abney
    Purpose: To play hangman


*/
package hangman;

import java.util.Random;
import java.util.Scanner;

public class Hangman {
    
    public static void main(String[] args) {
        
        // Starts game
        boolean game = true;
        String replay = "";
        while ( game == true ){
            
            // Variables and such
            replay = "";
            String[] Wordlist = { "fun", "random", "brown", "football", "outside", "computer",
                "basketball", "rain", "europe", "america", "hot", "food", "cool", "ice", 
                "body", "person" };
            Random WordChoice = new Random();
            String Word = Wordlist[ WordChoice.nextInt( Wordlist.length ) ];
            int WordLength = Word.length();
            Scanner Start = new Scanner( System.in );
            Scanner Answer = new Scanner( System.in );
            String Guess = "_";
            int Chances = 5;
            boolean WordDone = false;
            String LettersGuessed = "";
            int NumberOfGuesses = 0;
            String UnderScore = UnderScores( Word );
            
            // Welcoming and introduction
            System.out.println("Welcome to hangman.");
            String NewWord = LetterInWord( Word, UnderScore, Guess );
            UnderScore = NewWord;
            System.out.println("The chosen word is a length of " + WordLength + " long.");
            System.out.println( UnderScore );
            System.out.println("Type a letter to guess! Good luck.");
            
            // Game
            while ( WordDone == false ){
                Guess = Answer.nextLine();
                System.out.println();
                if ( !AlreadyGuessed( LettersGuessed, Guess ) ){
                    NumberOfGuesses = NumberOfGuesses + 1;
                    LettersGuessed += Guess + ", ";
                    
                    // Checks if letter is in word for true
                    if ( IsLetterInWord( Guess,Word ) == true ){
                        System.out.println("Good job! The letter " + Guess + " was in the word.");
                        NewWord = LetterInWord( Word, UnderScore, Guess );
                        UnderScore = NewWord;
                        System.out.println("Guess another letter. You still have " + Chances + " chances remaining.");
                        System.out.println("The letters you have guessed are: ");
                        System.out.println( LettersGuessed.substring( 0,LettersGuessed.length()-2) + "." );
                        System.out.println( UnderScore );
                        WordDone = IsWordDone( Word, NewWord );
                    } else {
                        System.out.println("Sorry the letter " + Guess + " is not in the word.");
                        Chances = Chances - 1;
                        
                        // When player fails
                        while ( Chances <= 0 ){
                            System.out.println("Sorry you are out of tries. Would you like to replay(Yes/No): ");
                            replay = Start.nextLine();
                            if ( replay.equalsIgnoreCase("Yes") ){
                                WordDone = true;
                                break;
                            } else if ( replay.equalsIgnoreCase("No") ){
                                System.out.println("Goodbye.");
                                System.exit(0);
                            }
                        }
                        if ( WordDone == false ){
                            System.out.println("Try again. You have " + Chances + " remaining.");
                            WordDone = IsWordDone( Word, NewWord );
                            System.out.println("The letters you have guessed are: ");
                            System.out.println( LettersGuessed.substring( 0,LettersGuessed.length()-2) + "." );
                            System.out.println( UnderScore );
                            System.out.println();
                            System.out.print("Enter another letter: ");
                        }
                    }
                } else {
                    System.out.println("Please enter a letter you haven't guessed: ");
                    System.out.println("The letters you have guessed are: ");
                    System.out.println( LettersGuessed.substring( 0,LettersGuessed.length()-2) + "." );
                }
            }
            
            // If want to replay
            if ( replay.equalsIgnoreCase("Yes") ){
                game = true;
            } else if ( replay.equalsIgnoreCase("No") ){
                System.exit(0);
            } else {
                System.out.println("Good Job! Would you like to play again?");
                replay = Start.nextLine();
                if (replay.equalsIgnoreCase("Yes") ){
                    game = true;
                }
                if (replay.equalsIgnoreCase("No") ){
                    System.exit(0);
                }
            }
            System.out.println();
        }
    }
    
    
    
    // Finds out if letter is in the word
    public static boolean IsLetterInWord( String guess, String word ){
        boolean LetterWord = false;
        int Index = 0;
        String CurrentLetter;
        
        while ( Index < word.length() ){
            CurrentLetter = word.substring( Index, Index + 1 );
            if ( guess.equalsIgnoreCase( CurrentLetter ) ){
                LetterWord = true;
            }
            Index = Index + 1;
        }
        return LetterWord;
    }
    
    
    // Place letter in word
    public static String LetterInWord( String Word, String Wordold, String guess ){
        char[] temp = new char[ Word.length() ];
        for ( int i = 0; i < Word.length(); i++ ){
            temp[i] = Word.charAt(i);
        }
        String Guess = guess.toUpperCase();
        for ( int i = 0; i<Word.length(); i++ ){
            if ( Guess.charAt(0) == Word.charAt(i) && Wordold.charAt(i) == '*' ){
                temp[i] = Guess.charAt(0);
            } else if ( Wordold.charAt(i) == '*' ){
                temp[i] = '*';
            }
        }
        for ( int i = 0; i<Word.length(); i++ ){
            if ( guess.toLowerCase().charAt(0) == Word.charAt(i) && Wordold.charAt(i) == '*' ){
                if ( guess.charAt(0) >= 'a' && guess.charAt(0) <= 'z' ){
                    temp[i] = (char) ( guess.charAt(0) - 32 );
                } else {
                    temp[i] = (char) ( guess.charAt(0) );
                }
            } else if ( Wordold.charAt(i) == '*' ){
                temp[i] = '*';
            }
        }
        String WordNow = new String( temp );
        return WordNow;
    }
    
    
    // Checks if the word is done
    public static boolean IsWordDone( String Word, String WordString ){
        boolean WordDone = false;
        if ( Word.equalsIgnoreCase( WordString ) ){
            WordDone = true;
        }
        return WordDone;
    }
    
    
    // Sets the underscored word
    public static String UnderScores( String Word ){
		char[] temp = new char[ Word.length() ];
		for ( int i = 0; i < Word.length(); i++ )
		{
			temp[i] = Word.charAt(i);
		}
		for ( int i = 0; i < temp.length; i++ ){
			temp[i] = '*';
		}
		String NewWord = new String( temp );
		return NewWord;
	}
    
    
    // Checks if guessing a letter that has already been guessed
    public static boolean AlreadyGuessed( String Guessed, String LetterGuessing ){
        char Letter = LetterGuessing.charAt(0);
        for (int i = 0; i < Guessed.length(); i++) {
            if ( Guessed.charAt(i) == Letter || Guessed.charAt(i) + 32 == Letter ){
                return true;
            }
        }
        return false;
    }
}