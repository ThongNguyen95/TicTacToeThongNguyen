/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoethongnguyen;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import static javafx.application.Application.launch;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


/**
 *
 * @author: Thong Nguyen
 * Project: Tic Tac Toe (with AI)
 * Date: 04/07/2017
 */
public class TicTacToeThongNguyen extends Application {
    

    @Override
    public void start(Stage primaryStage) {
        //Background and Window setup**********
        Group root = new Group();
        Scene scene = new Scene(root,512,512);
        
        ResizableCanvas canvas = new ResizableCanvas ();
        root.getChildren().add(canvas);
        
        canvas.widthProperty().bind(scene.widthProperty());
        canvas.heightProperty().bind(scene.heightProperty());
        
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Image background = new Image("background2.jpg");
        
        Sprite[][] boxList = new Sprite[3][3];
       
       //Load Buttons'images ************
       Sprite title = new Sprite();
       title.setImage("Title.png");
       
       Sprite playBtn = new Sprite();
       playBtn.setImage("PlayButton.png");
       
       Sprite closeBtn = new Sprite();
       closeBtn.setImage("CloseButton.png");
              
       Sprite pvpBtn = new Sprite();
       pvpBtn.setImage("PvPButton.png");
             
       Sprite pvcBtn = new Sprite();
       pvcBtn.setImage("PvCButton.png");
            
       Sprite easyBtn = new Sprite();
       easyBtn.setImage("EasyButton.png");
     
       Sprite diffBtn = new Sprite();
       diffBtn.setImage("DifficultButton.png");
      
       Sprite replayBtn = new Sprite();
       replayBtn.setImage("ReplayButton.png");
       
       Sprite mainMenuBtn = new Sprite();
       mainMenuBtn.setImage("MainMenuButton.png");
  
       
       
       //Game Logic control values*********
            //for AI
       ArrayList<int[]> scoreList = new ArrayList<>();
       
            
       IntValue turn = new IntValue(0);     // 0. X
                                            // 1. O
       IntValue moveCount = new IntValue(0);
       IntValue gameStatus = new IntValue(0);   //0.Menu 
                                                //1.In-game 
                                                //2.Result
       
       IntValue menuStatus = new IntValue(0);   //0.Main Menu
                                                //1.Mode Menu
                                                //2.Difficulty Menu

       IntValue result = new IntValue(0);   //0.None 
                                            //1.X win 
                                            //2.O win
                                            //3. Tie       

                                            
       IntValue mode = new IntValue(1); //0. Player vs Player
                                        //1. Player vs AI (Easy)
                                        //2. Player vs AI (Difficult)
       Image boxE = new Image("box.png");
       Image boxX = new Image("boxX.png");
       Image boxO = new Image("boxY.png");

       for (int i=0; i<3;i++) {
           for (int j=0; j<3;j++) {
               Sprite box = new Sprite();
               box.setImage(boxE);
               boxList[i][j] = box;

           }
       }
       
       //EVENT WHEN MOUSE CLICK DETECT************
       scene.setOnMouseClicked((MouseEvent e) -> {
           //Switch for the whole game
           switch(gameStatus.value) {
               case 0:
                   //Switch for Menu
                   switch (menuStatus.value) {
                       case 0:
                           if (playBtn.getBoundary().contains(e.getX(), e.getY()))
                               menuStatus.setValue(1);
                           else if (closeBtn.getBoundary().contains(e.getX(), e.getY()))
                               primaryStage.close();
                           break;
                       case 1:
                           if (pvpBtn.getBoundary().contains(e.getX(), e.getY())) {
                               gameStatus.setValue(1);
                               mode.setValue(0);
                           }
                           else if (pvcBtn.getBoundary().contains(e.getX(), e.getY())) {
                               menuStatus.setValue(2);
                           } else if ((mainMenuBtn.getBoundary().contains(e.getX(), e.getY()))) {
                               menuStatus.setValue(0);
                           }
                           break;
                       case 2:
                           if (easyBtn.getBoundary().contains(e.getX(), e.getY())) {
                               mode.setValue(1);
                               gameStatus.setValue(1);
                           } else if ((diffBtn.getBoundary().contains(e.getX(), e.getY()))) {
                               mode.setValue(2);
                               gameStatus.setValue(1);
                           }
                           else if ((mainMenuBtn.getBoundary().contains(e.getX(), e.getY()))) {
                               menuStatus.setValue(0);
                           }
                           break;
                       default:
                           break;
                   }
                   
                   
                   
                   break;
               case 1:
                   for (int i=0; i<3;i++) {
                       for (int j=0; j<3;j++) {
                           if (boxList[i][j].getBoundary().contains(e.getX(),e.getY())){
                               if (boxList[i][j].getCheck().contains("empty")) {
                                   moveCount.value++;
                                   if (turn.value == 0) {
                                       boxList[i][j].setImage(boxX);
                                       boxList[i][j].setCheckTo("X");
                                       turn.setValue(1);
                                   } else {
                                       if (mode.value == 0) {
                                           boxList[i][j].setImage(boxO);
                                           boxList[i][j].setCheckTo("O");
                                           turn.setValue(0);
                                       }
                                   }
                               }
                           }
                       }
                   }
                   break;
               case 2:
                   if (replayBtn.getBoundary().contains(e.getX(), e.getY())) {
                       gameStatus.setValue(1);
                       result.setValue(0);
                       moveCount.setValue(0);
                       turn.setValue(0);
                       for (int i=0; i<3;i++) {
                           for (int j=0; j<3;j++) {
                               boxList[i][j].setCheckTo("empty");
                               boxList[i][j].setImage(boxE);
                           }
                       }
                   }
                   if (closeBtn.getBoundary().contains(e.getX(), e.getY())){
                       primaryStage.close();
                   }
                   
                   else if ((mainMenuBtn.getBoundary().contains(e.getX(), e.getY()))) {
                       gameStatus.setValue(0);
                       menuStatus.setValue(0);
                       result.setValue(0);
                       moveCount.setValue(0);
                       turn.setValue(0);
                       for (int i=0; i<3;i++) {
                           for (int j=0; j<3;j++) {
                               boxList[i][j].setCheckTo("empty");
                               boxList[i][j].setImage(boxE);
                           }
                       }
                   }
                   break;
               default:
                   break;
           }
        });

       //GAME LOOP
       new AnimationTimer() {
           @Override
           public void handle(long currentNanoTime) {
               gc.clearRect(0, 0, w(512,scene), h(512,scene));
               
               //keep track of objects' sizes when window is resized
               title.setPosition(w(20,scene), h(30,scene));
               title.setSize(w(439,scene),h(158,scene));
               
               playBtn.setPosition(w(150,scene), h(200,scene));
               playBtn.setSize(w(200,scene), h(100,scene));
               
               closeBtn.setSize(w(200,scene), h(100,scene));
               
               easyBtn.setPosition(w(150,scene), h(200,scene));
               easyBtn.setSize(w(200,scene), h(100,scene));
               
               pvpBtn.setPosition(w(50,scene), h(200,scene));
               pvpBtn.setSize(w(400,scene), h(100,scene));
               pvcBtn.setPosition(w(50,scene), h(300,scene));
               pvcBtn.setSize(w(400,scene), h(100,scene));
               
               diffBtn.setPosition(w(150,scene), h(300,scene));
               diffBtn.setSize(w(200,scene), h(100,scene));
               
               replayBtn.setPosition(w(50,scene), h(150,scene));
               replayBtn.setSize(w(200,scene), h(100,scene));
               
               mainMenuBtn.setPosition(w(340,scene), h(420,scene));
               mainMenuBtn.setSize(w(120,scene), h(60,scene));
               
               //GAME LOGIC*********
                
                //Check board status since last modification
                
                if (moveCount.value == 9) {
                   gameStatus.setValue(2);
                   result.setValue(3);
                }
                
                for (int i=0; i<3; i++){
                   
                   if ((boxList[i][0].getCheck().equals(boxList[i][1].getCheck()))&&
                           (boxList[i][1].getCheck().equals(boxList[i][2].getCheck()))) {
                       
                       if (boxList[i][1].getCheck().contains("X")) {
                           gameStatus.setValue(2);
                           result.setValue(1);
                           break;
                       
                       } else if (boxList[i][1].getCheck().contains("O")) {
                           gameStatus.setValue(2);
                           result.setValue(2);
                           break;
                       }
                   }
                   if ((boxList[0][i].getCheck().equals(boxList[1][i].getCheck()))&&
                           (boxList[1][i].getCheck().equals(boxList[2][i].getCheck()))) {
                       
                       if (boxList[1][i].getCheck().contains("X")) {
                           gameStatus.setValue(2);
                           result.setValue(1);
                           break;
                       
                       } else if (boxList[1][i].getCheck().contains("O")) {
                           gameStatus.setValue(2);
                           result.setValue(2);
                           break;
                       }
                   }
               }
               
               if (((boxList[0][0].getCheck().equals(boxList[1][1].getCheck()))&&
                           (boxList[1][1].getCheck().equals(boxList[2][2].getCheck()))) 
                       || ((boxList[0][2].getCheck().equals(boxList[1][1].getCheck()))&&
                           (boxList[1][1].getCheck().equals(boxList[2][0].getCheck())))) {
                   
                   if (boxList[1][1].getCheck().contains("X")) {
                       gameStatus.setValue(2);
                       result.setValue(1);
                   
                   } else if (boxList[1][1].getCheck().contains("O")) {
                       gameStatus.setValue(2);
                       result.setValue(2);
                   }
               }
               
               
               //Check board size, location and close button size
               switch (gameStatus.value) {
                   case 0:
                       closeBtn.setPosition(w(150,scene), h(300,scene));
                       break;
                   case 1:
                       setDefaultBoard(boxList, scene);
                       break;
                   case 2:
                       setFinalBoard(boxList,scene);
                       closeBtn.setPosition(w(50,scene), h(250,scene));
                       break;
                   default:
                       break;
               }               
               
               
               //AI
               //Easy AI
               if (turn.value == 1 && moveCount.value < 9 && gameStatus.value == 1) {
                   if (mode.value == 1) {
                       scoreList.clear();
                       for (int i=0; i<3; i++) {
                           for (int j=0; j<3; j++) {
                               if ("empty".equals(boxList[i][j].getCheck())) {
                                   scoreList.add(new int[3]);
                                   scoreList.get(scoreList.size()-1)[0] = i;
                                   scoreList.get(scoreList.size()-1)[1] = j;
                                   scoreList.get(scoreList.size()-1)[2] = get_score_easy(boxList,i,j,turn.value);
                               }
                           }
                       }
                       //get highest score
                       int[] highestScore = scoreList.get(0);
                       for (int i=0; i < scoreList.size(); i++) {
                           if (scoreList.get(i)[2] > highestScore[2]) {
                               highestScore[0] = scoreList.get(i)[0];
                               highestScore[1] = scoreList.get(i)[1];
                               highestScore[2] = scoreList.get(i)[2];
                           }
                       }
                       boxList[highestScore[0]][highestScore[1]].setCheckTo("O");
                       boxList[highestScore[0]][highestScore[1]].setImage(boxO);
                       turn.setValue(0);
                       moveCount.value++;
                   }
                   
                   //Difficult AI
                   else if (mode.value == 2) {
                       scoreList.clear();
                       for (int i=0; i<3; i++) {
                           for (int j=0; j<3; j++) {
                               if ("empty".equals(boxList[i][j].getCheck())) {
                                   scoreList.add(new int[3]);
                                   scoreList.get(scoreList.size()-1)[0] = i;
                                   scoreList.get(scoreList.size()-1)[1] = j;
                                   scoreList.get(scoreList.size()-1)[2]
                                           = get_score_hard(boxList,i,j,turn.value);
                               }
                           }
                       }
                       
                       //get highest score
                       int[] highestScore = scoreList.get(0);
                       for (int i=0; i < scoreList.size(); i++) {
                           if (scoreList.get(i)[2] > highestScore[2]) {
                               highestScore[0] = scoreList.get(i)[0];
                               highestScore[1] = scoreList.get(i)[1];
                               highestScore[2] = scoreList.get(i)[2];
                           }
                       }
                       boxList[highestScore[0]][highestScore[1]].setCheckTo("O");
                       boxList[highestScore[0]][highestScore[1]].setImage(boxO);
                       turn.setValue(0);
                       moveCount.value++;
                   }
               }               
               //END OF AI CODE


               //render
               gc.drawImage(background, 0, 0, scene.getWidth(), scene.getHeight());
               switch (gameStatus.value) {
                   case 0:
                       title.render(gc);
                       switch(menuStatus.value) {
                           case 0:
                               title.render(gc);
                               playBtn.render(gc);
                               closeBtn.render(gc);
                               break;
                           case 1:
                               pvpBtn.render(gc);
                               pvcBtn.render(gc);
                               mainMenuBtn.render(gc);
                               break;
                           case 2:
                               easyBtn.render(gc);
                               diffBtn.render(gc);
                               mainMenuBtn.render(gc);
                               break;
                           default:
                               break;
                       }
                       break;
                   case 1:
                       for (Sprite[] box : boxList) {
                           for (Sprite box2 : box)
                               box2.render(gc);
                       }
                       if (turn.value == 0) {
                           gc.setFill(Color.BLUE);
                           gc.setFont(Font.font("Arial", w(30,scene)));
                           gc.fillText("Player 1", w(49,scene), h(500,scene));
                           gc.strokeText("Player 1", w(49,scene), h(500,scene));
                       } else {
                           gc.setFill(Color.RED);
                           gc.setFont(Font.font("Arial", w(30,scene)));
                           gc.fillText("Player 2", w(358,scene), h(500,scene));
                           gc.strokeText("Player 2", w(358,scene), h(500,scene));
                       }
                       break;
                   case 2:
                       for (Sprite[] box : boxList) {
                           for (Sprite box2 : box)
                               box2.render(gc);
                       }  
                       mainMenuBtn.render(gc);
                       replayBtn.render(gc);
                       closeBtn.render(gc);
                       gc.setFont(Font.font("Arial Black", FontWeight.BOLD, w(50,scene)));
                       switch (result.value) {
                           case 1:
                               gc.setFill(Color.BLUE);
                               gc.fillText("Player 1 Win", w(90,scene), h(110,scene));
                               gc.strokeText("Player 1 Win", w(90,scene), h(110,scene));
                               break;
                           case 2:
                               gc.setFill(Color.RED);
                               gc.fillText("Player 2 Win", w(90,scene), h(110,scene));
                               gc.strokeText("Player 2 Win", w(90,scene), h(110,scene));
                               break;
                           case 3:
                               gc.setFill(Color.GREEN);
                               gc.fillText("TIE", w(200,scene), h(100,scene));
                               gc.strokeText("TIE", w(200,scene), h(100,scene));
                               break;
                           default:
                               break;
                       }   break;
                   default:
                       break;
               }
           }
       }.start();
       primaryStage.setTitle("Tic Tac Toe");
       primaryStage.setScene(scene);
       primaryStage.show();
    }
    
    
        //Make canvas resizable
    class ResizableCanvas extends Canvas {

        public ResizableCanvas() {
            // Redraw canvas when size changes.
            widthProperty().addListener(canvas -> draw());
            heightProperty().addListener(canvas -> draw());
        }

        private void draw() {
            double width = getWidth();
            double height = getHeight();

            GraphicsContext gc = getGraphicsContext2D();
            gc.clearRect(0, 0, width, height);
            
            gc.strokeLine(0, 0, width, height);
            gc.strokeLine(0, height, width, 0);
        }

        @Override
        public boolean isResizable() {
            return true;
        }

        @Override
        public double prefWidth(double height) {
            return getWidth();
        }

        @Override
        public double prefHeight(double width) {
            return getHeight();
        }
    }
    
    public static double w(double size, Scene scene) { //Width resize based on window
        double newSize;
        newSize = size * (scene.getWidth()/512);
        return newSize;
    }
    public static double h(double size, Scene scene) { //Length resize based on window
        double newSize;
        newSize = size * (scene.getHeight()/512);
        return newSize;
    }
    
    
//Sprite class - for game character, object, entity.
    public class Sprite {
        private String check ="empty";
        private Image image;
        private double positionX;
        private double positionY;
        private double velocityX;
        private double velocityY;
        private double width;
        private double height;
        
        public void setCheckTo(String value) {
            check = value;
        }
        public void setSpriteTo(Sprite val) {
            check = val.check;
            image = val.image;
            positionX = val.positionX;
            positionY = val.positionY;
            velocityX = val.velocityX;
            velocityY = val.velocityY;
            width = val.width;
            height = val.height;
        }
        public String getCheck() {
            return check;
        }
        public void setImage(Image i){
            image = i;
            width = i.getWidth();
            height = i.getHeight();
        }
        public void setImage(String filename) {
            Image i = new Image(filename);
            setImage(i);
        }
        public void setSize(double w, double l) {
            width = w;
            height = l;
        }   
        public void setPosition(double x, double y) {
            positionX = x;
            positionY = y;
        }
        public void setVelocity(double x, double y) {
            velocityX = x;
            velocityY = y;
        }
        public void addVelocity(double x, double y) {
            velocityX += x;
            velocityY += y;
        }
        public void update(double time){
            positionX += velocityX * time;
            positionY += velocityY * time;
        }
        public void render(GraphicsContext gc) {
            gc.drawImage(image, positionX, positionY, width, height);
        }
        public Rectangle2D getBoundary() {
            return new Rectangle2D(positionX, positionY,width,height);
        }
        public boolean intersects(Sprite s) {
            return s.getBoundary().intersects( this.getBoundary() );
        }
    }
    
    static void setDefaultBoard(Sprite[][] boxList, Scene scene) {
        double b = h(49,scene);
        for (int i=0; i<3;i++) {
            double a = w(49,scene);
            for (int j=0; j<3;j++) {
               boxList[i][j].setPosition(a, b);
               boxList[i][j].setSize(w(138,scene), h(138,scene));
               a += w(138,scene);
           }
           b += h(138,scene);
       }
    }
    static void setFinalBoard(Sprite[][] boxList, Scene scene) {
        double b = h(150,scene);
        for (int i=0; i<3;i++) {
            double a = w(256,scene);
            for (int j=0; j<3;j++) {
               boxList[i][j].setPosition(a, b);
               boxList[i][j].setSize(w(77,scene), h(77,scene));
               a += w(77,scene);
           }
           b += h(77,scene);
       }
    }
    

    public class IntValue {
        public int value;
        public IntValue(int val) {
            value = val;
        }
        public void setValue(int val) {
            value = val;
        }
    }

    
    
    //Get AI(difficult) response using minimax algorithm.
     int get_score_hard(Sprite[][] boxList,int a,int b,int turn) {
        Sprite[][] List = new Sprite[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++){
                Sprite temp = new Sprite();
                temp.setSpriteTo(boxList[i][j]);
                List[i][j] = temp;
            }
        }
        int tmpTurn = turn;
        int choiceScore;
        ArrayList<Integer> scoreList = new ArrayList<>();
        if (tmpTurn == 0) {
            List[a][b].setCheckTo("X");
            tmpTurn = 1;
        } else if (tmpTurn == 1) {
            List[a][b].setCheckTo("O");
            tmpTurn = 0;            
        }
        
        if (check_end_game(List)) {
            int score = 0;
            for (int i=0; i<3; i++){
                if ((List[i][0].getCheck().equals(List[i][1].getCheck()))&&
                        (List[i][1].getCheck().equals(List[i][2].getCheck()))) {
                    if (List[i][1].getCheck().contains("X")) {
                        score = -10;
                        break;
                    } else if (List[i][1].getCheck().contains("O")) {
                        score = 10;
                        break;
                    }
                }
                if ((List[0][i].getCheck().equals(List[1][i].getCheck()))&&
                        (List[1][i].getCheck().equals(List[2][i].getCheck()))) {
                    if (List[1][i].getCheck().contains("X")) {
                        score = -10;
                        break;
                    } else if (List[1][i].getCheck().contains("O")) {
                        score = 10;
                        break;
                    }
                }
            }
            if (((List[0][0].getCheck().equals(List[1][1].getCheck()))&&
                    (List[1][1].getCheck().equals(List[2][2].getCheck())))
                    || ((List[0][2].getCheck().equals(List[1][1].getCheck()))&&
                    (List[1][1].getCheck().equals(List[2][0].getCheck())))) {
                if (List[1][1].getCheck().contains("X")) {
                    score = -10;
                } else if (List[1][1].getCheck().contains("O")) {
                    score = 10;
                }
            }
            return score;
        } else {
            for (int i=0; i < 3; i++) {
                for (int j=0; j<3; j++) {
                    if ("empty".equals(List[i][j].getCheck())) {
                        scoreList.add(get_score_hard(List,i,j,tmpTurn));
                    }
                }
            }
            choiceScore = scoreList.get(0);
            if (tmpTurn == 1) { //get highest score
                for (int i=0; i < scoreList.size(); i++) {
                    if (scoreList.get(i) > choiceScore) {
                        choiceScore = scoreList.get(i);
                    }
                }
            } else if (tmpTurn == 0) { //get lowest score
                for (int i=0; i < scoreList.size(); i++) {
                    if (scoreList.get(i) < choiceScore) {
                        choiceScore = scoreList.get(i);
                    }
                }
            }
            return choiceScore;
        }
     }
     
     //Get AI(easy) response using minimax algorithm.
     int get_score_easy(Sprite[][] boxList,int a,int b,int turn) {
         Sprite[][] List = new Sprite[3][3];
         for (int i = 0; i < 3; i++) {
             for (int j = 0; j < 3; j++){
                 Sprite temp = new Sprite();
                 temp.setSpriteTo(boxList[i][j]);
                 List[i][j] = temp;
             }
         }
         int tmpTurn = turn;
         int choiceScore;
         ArrayList<Integer> scoreList = new ArrayList<>();
         if (tmpTurn == 0) {
             List[a][b].setCheckTo("X");
             tmpTurn = 1;
         } else if (tmpTurn == 1) {
             List[a][b].setCheckTo("O");
             tmpTurn = 0;
         }
         if (check_end_game(List)) {
             int score = 0;
             for (int i=0; i<3; i++){
                 if ((List[i][0].getCheck().equals(List[i][1].getCheck()))&&
                         (List[i][1].getCheck().equals(List[i][2].getCheck()))) {
                     if (List[i][1].getCheck().contains("X")) {
                         score = -10;
                         break;
                     } else if (List[i][1].getCheck().contains("O")) {
                         score = 10;
                         break;
                     }
                 }
                 if ((List[0][i].getCheck().equals(List[1][i].getCheck()))&&
                         (List[1][i].getCheck().equals(List[2][i].getCheck()))) {
                     if (List[1][i].getCheck().contains("X")) {
                         score = -10;
                         break;
                     } else if (List[1][i].getCheck().contains("O")) {
                         score = 10;
                         break;
                     }
                 }
             }
             if (((List[0][0].getCheck().equals(List[1][1].getCheck()))&&
                     (List[1][1].getCheck().equals(List[2][2].getCheck())))
                     || ((List[0][2].getCheck().equals(List[1][1].getCheck()))&&
                     (List[1][1].getCheck().equals(List[2][0].getCheck())))) {
                 if (List[1][1].getCheck().contains("X")) {
                     score = -10;
                 } else if (List[1][1].getCheck().contains("O")) {
                     score = 10;
                 }
             }
             return score;
         } else {
             for (int i=0; i < 3; i++) {
                 for (int j=0; j<3; j++) {
                     if ("empty".equals(List[i][j].getCheck())) {
                         scoreList.add(get_score_easy(List,i,j,tmpTurn));
                     }
                 }
             }
             choiceScore = scoreList.get(0);
             if (turn == 1) { //get highest score
                 for (int i=0; i < scoreList.size(); i++) {
                     if (scoreList.get(i) > choiceScore) {
                         choiceScore = scoreList.get(i);
                     }
                 }
             } else if (turn == 0) { //get lowest score
                 for (int i=0; i < scoreList.size(); i++) {
                     if (scoreList.get(i) < choiceScore) {
                         choiceScore = scoreList.get(i);
                     }
                 }
             }
             return choiceScore;
         }
     }
     
     //Check if the game is in end state.
    static boolean check_end_game(Sprite[][] List) {
        boolean end = false;
        for (int i=0; i<3; i++){
                if ((List[i][0].getCheck().equals(List[i][1].getCheck()))&&
                        (List[i][1].getCheck().equals(List[i][2].getCheck()))) {
                    if (List[i][1].getCheck().contains("X")) {
                        end = true;
                        break;
                    } else if (List[i][1].getCheck().contains("O")) {
                        end = true;
                        break;
                    }
                }
                else if ((List[0][i].getCheck().equals(List[1][i].getCheck()))&&
                        (List[1][i].getCheck().equals(List[2][i].getCheck()))) {
                    if (List[1][i].getCheck().contains("X")) {
                        end = true;
                        break;
                    } else if (List[1][i].getCheck().contains("O")) {
                        end = true;
                        break;
                    }
                }
        }
        if (((List[0][0].getCheck().equals(List[1][1].getCheck()))&&
                (List[1][1].getCheck().equals(List[2][2].getCheck())))
                || ((List[0][2].getCheck().equals(List[1][1].getCheck()))&&
                (List[1][1].getCheck().equals(List[2][0].getCheck())))) {
            if (List[1][1].getCheck().contains("X")) {
                end = true;
            } else if (List[1][1].getCheck().contains("O")) {
                end = true;
            }
        }
        int count = 0;
        for (int i=0; i < 3; i++) {
            for (int j=0; j < 3; j++) {
                if (!"empty".equals(List[i][j].getCheck())) {
                    count++;
                }
            }
        }
        if (count == 9) {
            end = true;
        }
        return end;
    }
    

    public static void main(String[] args) {
        launch(args);
    }
    
}
