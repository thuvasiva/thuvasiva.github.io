 //NAME: Thuvaragan Sivayoganathan
 //DATE: 16/11/2020
 //VERSION:1
 //BRIEF OVERVIEW OF PURPOSE: Create a program which simulates a pointless game. Level 7 Version.

 import java.util.Scanner; // Needed to make Scanner available
 import java.util.Random;  // Needed to generate random numbers
 import java.io.*; // Needed for file input/output
 
 class PointlessL7
 {
     public static void main (String [] a) throws IOException
     {
         chooseOption(); //Calls the chooseOption Method()
         System.exit(0);
     }
     
     //Asks the question and prompts the user for input
     public static String askQuestionString(String message)
     {
         String answer;
         System.out.println(message);
         Scanner scanner = new Scanner(System.in);
         answer = scanner.nextLine();
         return answer;
         
     }
     
     //Asks a question and prompts the user for integer input.
     public static int askQuestionInt(String message)
     {
         Scanner scanner = new Scanner(System.in);
         System.out.println(message);
         System.out.println("Please enter a number.");
         int input = Integer.parseInt(scanner.nextLine());
         while(input <= 0)
         {
             System.out.println("Invalid Input");
             input = Integer.parseInt(scanner.nextLine());
         }
         
         return input;
     }
     
     
     //Checks points of given answer
     public static int checkPoints(String UserAnswer, QuestionBank bank, int randomQuestion)
     {
         int points;
         Boolean flaggedpoints = false;
         if(UserAnswer.equals(getAnswer(bank, randomQuestion, 0)))
         {
             System.out.println("Correct Answer. Your score is " + getScore(bank,randomQuestion, 0 ) + "." );
             points = getScore(bank,randomQuestion, 0);
             flaggedpoints = checkFlag(randomQuestion, bank, UserAnswer, 0);
             
             if(flaggedpoints.equals(true))
             {  
                 System.out.println("However, either you or someone else have already said this, so you get 100.");
                 points = 100;
             }
         
         }
         else if(UserAnswer.equals(getAnswer(bank,randomQuestion, 1)))
         { 
             System.out.println("Correct Answer. Your score is " + getScore(bank, randomQuestion, 1) + "."); 
             points = getScore(bank, randomQuestion, 1);
             flaggedpoints = checkFlag(randomQuestion, bank, UserAnswer, 1);
             
             if(flaggedpoints.equals(true))
             {  
                 System.out.println("However, either you or someone else have already said this, so you get 100.");
                 points = 100;
             }
         }
         else if(UserAnswer.equals(getAnswer(bank,randomQuestion, 2)))
         {
             System.out.println("Correct Answer. Your score is " + getScore(bank,randomQuestion, 2) + ".");
             points = getScore(bank,randomQuestion, 2);
             flaggedpoints = checkFlag(randomQuestion, bank, UserAnswer, 2);
             
             if(flaggedpoints.equals(true))
             {  
                 System.out.println("However, either you or someone else have already said this, so you get 100.");
                 points = 100;
             }
         }
         else if(UserAnswer.equals(getAnswer(bank,randomQuestion, 3)))
         {
             System.out.println("Correct Answer. Your score is " + getScore(bank,randomQuestion, 3) + ".");
             points = getScore(bank,randomQuestion, 3);
             flaggedpoints = checkFlag(randomQuestion, bank, UserAnswer, 3);
             if(flaggedpoints.equals(true))
             {  
                 System.out.println("However, either you or someone else have already said this, so you get 100.");
                 points = 100;
             }
          }
          else if(UserAnswer.equals(getAnswer(bank,randomQuestion, 4)))
          {
              System.out.println("Correct Answer. Your score is " + getScore(bank,randomQuestion,4) + ".");
              points = getScore(bank,randomQuestion, 4);
              flaggedpoints = checkFlag(randomQuestion, bank, UserAnswer, 4);
              if(flaggedpoints.equals(true))
              {  
                  System.out.println("However, either you or someone else have already said this, so you get 100.");
                  points = 100;
              } 
          }
         else
         {
             System.out.println("Wrong answer.");
             System.out.println("Your score is 100.");
             points = 100;
         }     
  
         return points;
     }
 
     
     //Creates Question variables and a QuestionBank variable out of them and calls various methods to run the game.
     public static void Start() 
     {   
         try
         {
             
             String filename = askQuestionString("Which file do you want to load? (Format = name.txt)"); 
             BufferedReader pointlessFileInput = new BufferedReader(new FileReader(filename));
                 
             Question[] questions = new Question[4];
             for(int i = 0; i<questions.length; i++){
                String qtext = pointlessFileInput.readLine();
                String[] a = new String[5];
	     	int[] s = new int[5];
 
             	for(int j = 0; j<a.length; j++){
		    a[j] = pointlessFileInput.readLine();
	     	}
	     	for(int k = 0; k<s.length; k++){
		    s[k] = Integer.parseInt(pointlessFileInput.readLine());
                }
                questions[i] = createQuestion(qtext,a[0],a[1],a[2],a[3],a[4],s[0],s[1],s[2],s[3],s[4]);
             
	     }

	     QuestionBank bank = createQuestionBank(questions[0],questions[1],questions[2],questions[3]);
 
             final int numberOfPlayers = askQuestionInt("How many players?");
             final int numberOfQuestionsEach = 3;
             int[] finalscores = new int[numberOfPlayers];
      
             for(int i = 0; i< numberOfPlayers; i++)
             { 
          
                 System.out.println("Player " + (i+1));
                 Welcome();
          
                 String answer;
                 int finalpoints = 0;
       
          
                 for(int j = 1; j<= numberOfQuestionsEach; j++)
                 {
                     int randomQuestion = randomNumber();
      
                     answer = askQuestionString(getQuestion(bank, randomQuestion));
                     finalpoints += checkPoints(answer, bank, randomQuestion);
                     System.out.println();  
                 }    
          
                 System.out.println("Your final total in Pointless is " + finalpoints + ". Thanks for playing!");
          
                 finalscores[i] = finalpoints;
          
                 System.out.println(); 
 
          
             }
          
             for(int k = 0; k< numberOfPlayers; k++)
             {
                 System.out.print("Player " + (k+1) + ": " + finalscores[k] + ". ");
             }
          
             System.out.println();
             sortScores(finalscores);
     
         }
         
         catch(IOException fileNotFound)
         {
             System.out.println("The file you provided does not exist.");
             Start();
         
         }
         
         catch(NumberFormatException notANumber)
         {
             System.out.println("The file is incomplete or does not contain a number for a certain score.");
             Start();
         }
 
     }
 
       
     //Welcomes the player to the game
     public static void Welcome()
     {
         System.out.println("Welcome to Pointless. This is a game where the lowest points wins!");
         System.out.println("Every question you will be asked to give an answer and more obscure the answer you gave is the lower it scores.");
         System.out.println("Make sure not to say an answer someone else has already said.");
         System.out.println("Make sure not to say the same answer again if you get asked the same question again.");
         System.out.println("Always give a full answer. When concerning names give a full name!");
         System.out.println("Good Luck!");
         System.out.println();
         
     }
     
     //Creates a new QuestionBank variable
     public static QuestionBank createQuestionBank(Question q1, Question q2, Question q3, Question q4 )
     {   
         QuestionBank bank = new QuestionBank();
         bank.totalQuestions[0] = q1;
         bank.totalQuestions[1] = q2;
         bank.totalQuestions[2] = q3;
         bank.totalQuestions[3] = q4;
         return bank;
     }
     
     // Creates a new Question variable and assigns the individual fields
     public static Question createQuestion(String q, String a1, String a2, String a3, String a4, String a5, int s1, int s2, int s3, int s4, int s5)
     {
         Question questionNumber = new Question();
         questionNumber.score1 = s1;
         questionNumber.score2 = s2;
         questionNumber.score3 = s3;
         questionNumber.score4 = s4;
         questionNumber.score5 = s5;
     
         questionNumber.answer1 = a1;
         questionNumber.answer2 = a2;
         questionNumber.answer3 = a3;
         questionNumber.answer4 = a4;
         questionNumber.answer5 = a5;
     
         questionNumber.question = q;
     
         questionNumber.flags = createFlagArray();
     
     
         return questionNumber;
     }
     
     //Retrieves value of question from the Question field of the QuestionBank variable
     public static String getQuestion(QuestionBank bank, int noOfQuestion)
     {
         return bank.totalQuestions[noOfQuestion].question;
     }
     
     //Gets the answer of a question from the question bank
     public static String getAnswer(QuestionBank bank,int noOfQuestion, int answerNumber)
     {  
         String[] answers= {bank.totalQuestions[noOfQuestion].answer1, bank.totalQuestions[noOfQuestion].answer2, bank.totalQuestions[noOfQuestion].answer3, bank.totalQuestions[noOfQuestion].answer4,bank.totalQuestions[noOfQuestion].answer5};
         return answers[answerNumber];
     }
     
     //Gets the score of an answer to a question in the question bank
     public static int getScore(QuestionBank bank, int noOfQuestion, int scoreNumber)
     { 
         int[] scores = {bank.totalQuestions[noOfQuestion].score1, bank.totalQuestions[noOfQuestion].score2, bank.totalQuestions[noOfQuestion].score3, bank.totalQuestions[noOfQuestion].score4, bank.totalQuestions[noOfQuestion].score5};
         return scores[scoreNumber];
     }
     
     
     //Creates an array of flags with all values false
     public static Boolean[] createFlagArray()
     {
         Boolean[] flagArray = {false,false,false,false,false};
         return flagArray;
     }
     
     //Checks and changes flags of whether the answer has been given already
     public static Boolean checkFlag(int randomQuestion, QuestionBank bank, String UserAnswer, int answerNumber)
     {
      
         if(getFlag(bank, randomQuestion, answerNumber) == true)
         {
             return true;
         }
         if(UserAnswer.equals(getAnswer(bank, randomQuestion, answerNumber)))
         {
             setFlag(bank, randomQuestion, answerNumber);
         }
         return false;
     }
     
     //Sets the value of an individual flag related to the answer of a specific question in the question bank
     public static void setFlag(QuestionBank bank, int randomQuestion, int answerNumber)
     {
         bank.totalQuestions[randomQuestion].flags[answerNumber] = true;
     }
     
     //Gets the value of an individual flag related to the answer of a specific question in the question bank
     public static Boolean getFlag(QuestionBank bank, int randomQuestion, int answerNumber)
     {  
         return bank.totalQuestions[randomQuestion].flags[answerNumber];
     }
 
     
     //Generates a random number
     public static int randomNumber()
     {
         int number;
         Random random = new Random();
 
         number = random.nextInt(4);
         return number;
     }
     
     //Sorts the scores of the players
     public static void sortScores(int[] finalscores)
     {   
         int temporaryScore;
         for(int j = 0; j<finalscores.length; j++)
         {
             for(int i = 0; i<finalscores.length-1-j; i++)
             {
                 if(finalscores[i] > finalscores[i+1])
                 {  
                     temporaryScore = finalscores[i];
                     finalscores[i] = finalscores[i+1];
                     finalscores[i+1] = temporaryScore;
                 }
             }
         }
  
         System.out.println("--------------------------------");
         System.out.println("          Score Table           ");
         for(int i = 0; i<finalscores.length; i++)
         {
             System.out.println((i+1) + ". " + finalscores[i]);
         }
         System.out.println("--------------------------------");
         System.out.println("Remember, the lowest scores wins!");
     } 
     
     //ALlows the user to create their own question bank
     public static void createNewBank() 
     {    
         try
         {
           System.out.println("You will now be creating a new Question Bank file compatible with Pointless.");
           System.out.println("A Question Bank consists of 4 Questions, each with 5 Answers and 5 Scores");    
           
           String filename = askQuestionString("Please enter the file name of the new file you want to create. (eg. Name.txt)");
           Scanner scanner = new Scanner(System.in);
           PrintWriter pointlessFileOutput = new PrintWriter(new FileWriter(filename));
           for(int i = 1; i<= 4; i++)
           {    
               System.out.println("Enter question " + i + ".");
               pointlessFileOutput.println("Question: " + scanner.nextLine());
               System.out.println("Enter 5 answers. Enter each answer on a new line.");
               for(int j = 1; j <= 5; j++)
               {
                   pointlessFileOutput.println(scanner.nextLine());
               }
               
               System.out.println("Enter 5 scores. Enter each number on each line.");
               
               for(int k = 1; k <= 5; k++)
               {     
                   pointlessFileOutput.println(scanner.nextLine());
               }
           }   
           
           System.out.println("You have successfully made a question bank!");
           
           pointlessFileOutput.close();
         }
     
         catch(IOException failure)
         {
             System.out.println("You did not successfully make a question bank, please try again!");
         }
         
     } 
     
      //Allows the user to choose whether to create a question bank or play exit
      public static void chooseOption() 
      {
          System.out.println("Choose an option: A) Create a new Question Bank or  B) Play Pointless or C) Exit");
          Scanner scanner = new Scanner(System.in);
          String choice = scanner.nextLine(); 
          while(!choice.equals("A") & !choice.equals("B") & !choice.equals("C"))
          {
               System.out.println("Choose either A or B or C");
               choice = scanner.nextLine();
          }
          if(choice.equals("A"))
          {
              createNewBank();
          }
          else if(choice.equals("B"))
          {
              Start();
          }
          else if(choice.equals("C"))
          {
              System.exit(0);
          }
          
      }
 
      
 }
 
 class Question
 {
      String question;
      String answer1;
      String answer2;
      String answer3;
      String answer4;
      String answer5;
      int score1;
      int score2; 
      int score3;
      int score4;
      int score5;
      Boolean[] flags = new Boolean[5];
          
 }
 
 class QuestionBank
 {
      Question[] totalQuestions = new Question[4];
 }    