package Sample_Testing;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Random;

/*
Author: Saichandan, Deepika, Rithika, Sheshi Rekha
 * This application is a procedure for Tic-Tac-Toe game. It uses a minimax
 * method with alpha-beta pruning for its move decision tree. */
 


public class tic_toe_group6 extends JFrame implements ActionListener
{

	//Global variables 
	private JButton new_button;
	private JPanel BoardPanel, TextPanel, ButtonPanel;
	private JLabel title, score_title;
	private JRadioButton ORadBtn, XRadBtn, ComputerFirstBtn, HumanFirstBtn, EasyBtn, MediumBtn, HardBtn;

	private boolean Human_First = true;

	private int Difficultlevel = 2;				//Variable that changes the amount of looks ahead and the intelligence used if computer goes first
	private int Lookahead = 2;		//Variable that contains the amount of looks ahead the minimax algorithm will do
	private int count = 0;		//Variable that keeps track of the looks ahead that have been done through recursion

	private int Score_X  = 0;				//Total score for human
	private int Score_Y = 0;			//Total score for CPU
	int[] Win_Comb = new int[4];			//Final winning combination
	Button_Tic_Tac_Toe[] final_Win_Comb = new Button_Tic_Tac_Toe[4];	//Final winning combination

	public boolean won = false;				//Variable to tell if a winning move has been achieved

	char Human_Selection = 'X';
	char CPU_Selection = 'O';

	private char Board_Panel[][][];				//Configuration of the BoardPanel that is manipulated in the minimax algorithm
	private Button_Tic_Tac_Toe[][][] button_access;	//Button array allows direct access to all buttons on the GUI itself


	public static void main(String a[])
	{
		new tic_toe_group6();
	}

	/*
	 * TicTacToeButton is a private class that extends JButton and adds information to determine the location
	 * to the main array
	 */
	private class Button_Tic_Tac_Toe extends JButton
	{
		public int Row_Box;
		public int Column_Box;
		public int BoardPanel_Box;
    //this.setFont(new Font("Arial", Font.PLAIN, 10));

	}

	/*
	 * OneMove holds information for one potential move. This is used to check if a
	 * move is a win or not
	 */
	public class Board_Movement
	{
		int Board_dimension;
		int Board_row;
		int Board_column;
	}


	/*
	 * constructor
	 */
	public tic_toe_group6()
	{
		super("3D Tic-Tac-Toe!");
		setSize(600,800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Board_Panelting();
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}


    /*
     * Board_Design allows the game BoardPanel which is drawn around the TicTacToeButtons without
     * interference.
     */
	public class Board_Design extends JPanel
	{
		protected void paintComponent(Graphics g)
		{
			super.paintComponent(g);

			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(2));

			//Board 0
			g2.drawLine(50, 95, 250, 95);
			g2.drawLine(25, 137, 225, 137);
			g2.drawLine(10, 179, 210, 179);
			g2.drawLine(110, 60, 35, 210);
			g2.drawLine(160, 60, 85, 210);
		    g2.drawLine(210, 60, 135, 210);
			

			//Board 1
			g2.drawLine(50, 270, 250, 270);
			g2.drawLine(25, 312, 225, 312);
			g2.drawLine(10, 354, 210, 354);
			g2.drawLine(120, 240, 45, 384);
			g2.drawLine(170, 240, 95, 384);
			g2.drawLine(220, 240, 145, 384);

			//Board 2
			g2.drawLine(50, 445, 250, 445);
			g2.drawLine(25, 487, 225, 487);
			g2.drawLine(10, 529, 210, 529);
			g2.drawLine(120, 410, 45, 562);
			g2.drawLine(170, 410, 95, 562);
			g2.drawLine(220, 410, 145, 562);

			//Board 3
			g2.drawLine(50, 622, 250, 622);
			g2.drawLine(25, 664, 225, 664);
			g2.drawLine(10, 706, 210, 706);
			g2.drawLine(120, 590, 45, 740);
			g2.drawLine(170, 590, 95, 740);
			g2.drawLine(220, 590, 145,740);

			//Draws Green line through the first and last winning position, indicating the location
			//for the win chance
			if(won)
			{
				g2.setColor(Color.GREEN);
				g2.drawLine(final_Win_Comb[0].getBounds().x + 27, final_Win_Comb[0].getBounds().y + 20,
						final_Win_Comb[3].getBounds().x + 27, final_Win_Comb[3].getBounds().y + 20);
			}
		}
	}

	/*
	* Board_Panelting is the method that will be building the GUI
	*/
	public void Board_Panelting()
	{
		//Creating the 2 arrays to represent the game
		Board_Panel = new char[4][4][4];
		button_access = new Button_Tic_Tac_Toe[4][4][4];

		BoardPanel = new Board_Design();
		ButtonPanel = new JPanel();
		TextPanel = new JPanel();

		//New-Game Button
		new_button = new JButton("New Game");
		new_button.setFont(new Font("Arial", Font.PLAIN, 15));
		new_button.setFocusPainted(false);
		new_button.setForeground(Color.GREEN);
		new_button.setBounds(350, 600, 130, 30);
		new_button.addActionListener(new ListenerClass());
		new_button.setName("new_button");
		new_button.setBackground(Color.YELLOW);
		//X,O Radio Button Declaration
		XRadBtn = new JRadioButton("X", true);
		ORadBtn = new JRadioButton("O");
		ORadBtn.setBackground(Color.GREEN);
		XRadBtn.setBackground(Color.GREEN);
		ORadBtn.setForeground(Color.RED);
		XRadBtn.setForeground(Color.RED);
		ORadBtn.setFocusPainted(false);
		XRadBtn.setFocusPainted(false);
		XRadBtn.setBounds(350, 420, 50, 50);
		ORadBtn.setBounds(400, 420, 50, 50);

		ButtonGroup xoSelect = new ButtonGroup();
		xoSelect.add(XRadBtn);
		xoSelect.add(ORadBtn);

		Listener_for_Movements list_1 = new Listener_for_Movements();
		XRadBtn.addActionListener(list_1);
		ORadBtn.addActionListener(list_1);

		//Initial move buttons
		HumanFirstBtn = new JRadioButton("Human First", true);
		ComputerFirstBtn = new JRadioButton("CPU First");
		ComputerFirstBtn.setBackground(Color.BLUE);
		HumanFirstBtn.setBackground(Color.BLUE);
		ComputerFirstBtn.setForeground(Color.yellow);
		HumanFirstBtn.setForeground(Color.yellow);
		ComputerFirstBtn.setFocusPainted(false);
		HumanFirstBtn.setFocusPainted(false);
		HumanFirstBtn.setBounds(350, 210, 150, 40);
		ComputerFirstBtn.setBounds(350, 180, 150, 40);

		ButtonGroup firstSelect = new ButtonGroup();
		firstSelect.add(ComputerFirstBtn);
		firstSelect.add(HumanFirstBtn);

		Listener_For_BoardPanel list_2 = new Listener_For_BoardPanel();
		ComputerFirstBtn.addActionListener(list_2);
		HumanFirstBtn.addActionListener(list_2);

		//Difficult level buttons
		EasyBtn = new JRadioButton("Easy");
		MediumBtn = new JRadioButton("Medium", true);
		HardBtn = new JRadioButton("Hard");
		EasyBtn.setBounds(350, 290, 150, 40);
		MediumBtn.setBounds(350, 320, 150, 40);
		HardBtn.setBounds(350, 350, 150, 40);

		ButtonGroup grp_btn = new ButtonGroup();
		grp_btn.add(EasyBtn);
		grp_btn.add(MediumBtn);
		grp_btn.add(HardBtn);

		Listener_for_Levels list_3 = new Listener_for_Levels();
		EasyBtn.addActionListener(list_3);
		MediumBtn.addActionListener(list_3);
		HardBtn.addActionListener(list_3);


		//Welcome header
		title = new JLabel("      Welcome to 3D Tic Tac Toe Game!");
		title.setFont(new Font("Tahoma", Font.BOLD, 16));
		title.setBackground(Color.lightGray);
		title.setForeground(Color.red);
		title.setOpaque(true);

		//ScoreBoard
		score_title = new JLabel("               You: " + Score_X + "   Computer: " + Score_Y);
		score_title.setFont(new Font("Tahoma", Font.BOLD, 12));
		score_title.setBackground(Color.lightGray);
		score_title.setForeground(Color.black);
		score_title.setOpaque(true);

		//Variables that determine the locations of the TicTacToeButtons as they are placed within loops
		int row_move = 25;
		int start_of_row = 50;

		int pos_of_x = 49;
		int pos_of_y = 43;
		int width_of_pointer = 60;
		int height_of_pointer = 50;

		//Variables to keep track of the current button being placed
		int b_num = 0;
		int row_count = 0;
		int col_count = 0;

		int count_for_BoardPanel = 0;

		//BoardPanel loop
		for (int i = 0; i <= 3; i++)
		{
			//Row loop
			for (int j = 0; j <= 3; j++)
			{
				//Column loop
				for(int k = 0; k <= 3; k++)
				{
					//Creating the new button, setting it to be empty in both arrays
					Board_Panel[i][j][k] = '-';
					button_access[i][j][k] = new Button_Tic_Tac_Toe();
					button_access[i][j][k].setFont(new Font("Arial Bold", Font.ITALIC, 20));
					button_access[i][j][k].setText("");
					//Making it transparent and add
					button_access[i][j][k].setContentAreaFilled(false);
					button_access[i][j][k].setBorderPainted(false);
					button_access[i][j][k].setFocusPainted(false);
					//Placing the button
					button_access[i][j][k].setBounds(pos_of_x, pos_of_y, width_of_pointer, height_of_pointer);
					//Setting information variables
					button_access[i][j][k].setName(Integer.toString(count_for_BoardPanel));
					button_access[i][j][k].BoardPanel_Box = b_num;
					button_access[i][j][k].Row_Box = row_count;
					button_access[i][j][k].Column_Box = col_count;
					//Adding action listener
					button_access[i][j][k].addActionListener(this);

					//Bump the column number 1, move the position that the next button will be placed to the right, and add the current button to the panel
					col_count++;
					count_for_BoardPanel++;
					pos_of_x += 49;
					getContentPane().add(button_access[i][j][k]);
				}

				//Reset the column number
				col_count = 0;
				row_count++;
				pos_of_x = start_of_row -= row_move;
				pos_of_y += 44;
			}

			//Reset row numbers and row shifts
			row_count = 0;
			row_move = 26;
			start_of_row = 58;
			b_num++;
			pos_of_x = start_of_row;
			pos_of_y += 2;
		}


		//Panel setup
		BoardPanel.setVisible(true);
		BoardPanel.setBackground(Color.lightGray);
		TextPanel.setVisible(true);
		ButtonPanel.setVisible(true);
		title.setVisible(true);

		TextPanel.setLayout(new GridLayout(2,1));
		TextPanel.add(title);
		TextPanel.add(score_title);
		TextPanel.setBounds(80, 0, 380, 30);

		add(XRadBtn);
		add(ORadBtn);
		add(HumanFirstBtn);
		add(ComputerFirstBtn);
		add(EasyBtn);
		add(MediumBtn);
		add(HardBtn);
		add(new_button);
		add(TextPanel);
		add(BoardPanel);


		setVisible(true);
	}

	/*
	* Listener_for_BoardPanel is an ActionListener that sets the starting player based on the players input.
	*/
	class Listener_For_BoardPanel implements ActionListener
	{
		

		public void actionPerformed(ActionEvent arg0)
		{
			erase_BoardPanel();
			title.setForeground(Color.BLACK);
			title.setText("                      Good luck!");

			if(ComputerFirstBtn.isSelected())
			{
				Human_First = false;

				if(!HardBtn.isSelected())
					random_movement_by_cpu();
				else
					movement_by_cpu();
			}
			else
			{
				Human_First = true;
			}
		}
	}

	/*
	* ListenerClass is an ActionListener that clears the BoardPanel, sets the text, and starts a new game, and will go first if the radio button for the CPU
	* to go first is selected
	*/
	class ListenerClass implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			erase_BoardPanel();
			title.setForeground(Color.BLACK);
			title.setText("                      Good luck!");

			if(!Human_First)
			{
				if(Difficultlevel == 3)
					movement_by_cpu();
				else
					random_movement_by_cpu();
			}
		}
	}
	/*
	* Listener_for_Movements is an ActionListener that changes the human and computer piece variables based on input from the user. It then clears the BoardPanel
	* and starts a new game
	*/
	class Listener_for_Movements implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			erase_BoardPanel();
			title.setForeground(Color.BLACK);
			title.setText("                      Good luck!");

			if(XRadBtn.isSelected())
			{
				Human_Selection = 'X';
				CPU_Selection = 'O';
			}
			else
			{
				Human_Selection = 'O';
				CPU_Selection = 'X';
			}

			if(!Human_First)
			{
				if(Difficultlevel == 3)
					movement_by_cpu();
				else
					random_movement_by_cpu();
			}
		}
	}

	/*
	* Listener_for_levels is an ActionListener that manipulates the difficulty variable and allows the user to change how aggresive or smart the
	* computer will play. The class itself just changes the global difficulty variable, and then starts a new game
	*/
	class Listener_for_Levels implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			erase_BoardPanel();
			title.setForeground(Color.BLACK);
			title.setText("                      Good luck!");

			if(EasyBtn.isSelected())
			{
				Difficultlevel = 1;
				Lookahead = 1;
			}
			else if(MediumBtn.isSelected())
			{
				Difficultlevel = 2;
				Lookahead = 2;
			}
			else
			{
				Difficultlevel = 3;
				Lookahead = 6;
			}

			if(!Human_First)
			{
				if(Difficultlevel == 3)
					movement_by_cpu();
				else
					random_movement_by_cpu();
			}
		}
	}

	/*
	* actionPerformed is the listener for all the buttons within the GUI. It takes in the input from the user if he/she clicks on a space, writes that information
	* to both the internal BoardPanel and the GUI BoardPanel, and simply checks if that move was a win. If it was, end the game and display the winning move/winning message.
	* If it wasn't, call random_movement_by_cpu()
	*/
	public void actionPerformed(ActionEvent e)
	{

		//Getting the button clicked's information and setting the arrays accordingly
		Button_Tic_Tac_Toe button = (Button_Tic_Tac_Toe)e.getSource();
		Board_Panel[button.BoardPanel_Box][button.Row_Box][button.Column_Box] = Human_Selection;
		button_access[button.BoardPanel_Box][button.Row_Box][button.Column_Box].setText(Character.toString(Human_Selection));
		button_access[button.BoardPanel_Box][button.Row_Box][button.Column_Box].setEnabled(false);

		Board_Movement newMove = new Board_Movement();
		newMove.Board_dimension = button.BoardPanel_Box;
		newMove.Board_row = button.Row_Box;
		newMove.Board_column = button.Column_Box;

		if(Win_Meth(Human_Selection, newMove))
		{
			title.setText("You beat me! Press New Game to play again.");
			title.setForeground(Color.RED);
			Score_X++;
			won = true;
			BoardPanel_game_stop();
			score_BoardPanel();
		}
		else
		{
			movement_by_cpu();
		}
	}

	/*
	 * score_BoardPanel() is used to update the score panel with the correct score when a win has occurred
	 */
	public void score_BoardPanel()
	{
		score_title.setText("               You: " + Score_X + "   Computer: " + Score_Y);
	}

	/*
	 * clearBoardPanel() is used to reset the BoardPanel when the new game button has been pressed. It also
	 * repaints the BoardPanel, clearing the winning move's line
	 */
	public void erase_BoardPanel()
	{
		repaint();
		won = false;
		count = 0;

		for (int i = 0; i <= 3; i++)
		{
			for (int j = 0; j <= 3; j++)
			{
				for(int k = 0; k <= 3; k++)
				{
					Board_Panel[i][j][k] = '-';
					button_access[i][j][k].setText("");
					button_access[i][j][k].setEnabled(true);
				}
			}
		}

		Win_Comb = new int[4];
	}

	/*
	 * disableBoardPanel() is used to disable the BoardPanel when a win has occurred, not allowing for any unintended
	 * clicks in the game space
	 */

	public void BoardPanel_game_stop()
	{
		int pointer = 0;
		for (int i = 0; i <= 3; i++)
		{
			for (int j = 0; j <= 3; j++)
			{
				for(int k = 0; k <= 3; k++)
				{
					if(checks_for_win_content(Win_Comb, Integer.parseInt(button_access[i][j][k].getName())))
					{
						button_access[i][j][k].setEnabled(true);
						button_access[i][j][k].setForeground(Color.RED);
						final_Win_Comb[pointer] = button_access[i][j][k];
						pointer++;
					}
					else
					{
						button_access[i][j][k].setEnabled(false);
					}
				}
			}
		}

		repaint();

	}

	/*
	 * Private method contains() is used in the process of checking the contents of the finalWin int array and
	 * changing the appropriate boxes to show the winning combination
	 */
	private boolean checks_for_win_content(int[] a, int k)
	{
		//Step through array
		for(int i : a)
		{	//Compare elements
			if(k == i)
				return true;
		}
		return false;
	}


	/*
	 * The method random_movement_by_cpu() is used when the difficulty setting is easy or medium and the computer is selected to go first.
	 * This is implemented because if the computer is allowed to move first using the minimax method, it is almost impossible for a
	 * human to win. Since the setting is on easy or medium, showing that the player might actually want to win, putting the first
	 * move in a random spot allows the game to be more competitive and fun. This method is not called when the setting is hard
	 * allowing for very aggressive play as the difficulty setting would suggest.
	 */
	private void random_movement_by_cpu()
	{
		Random random = new Random();
		int r = random.nextInt(4);
		int c = random.nextInt(4);
		int b = random.nextInt(4);
		Board_Panel[b][r][c] = CPU_Selection;
		button_access[b][r][c].setText(Character.toString(CPU_Selection));

		button_access[b][r][c].setEnabled(false);
	}

	/*
	 * computerPlays() is the main method used in the A.I. implementation of this game. It walks through each available move in the
	 * current game BoardPanel, then creates branches off of those moves using movement_by_cpu(), judging what the player will do in response to
	 * that potential move, and makes a move that is most promising in response to the possible humans most promising move.
	 */
	private void movement_by_cpu()
	{
		int winning_points;
		int human_points;
		Board_Movement next_pointer;
		int win_BoardPanel = -1;
		int win_row = -1;
		int win_column = -1;

		//Low number so the first bestScore will be the starting bestScore
		winning_points = -1000;
		//Walk through the entire game BoardPanel
		check:
		for (int i = 0; i <= 3; i++)
		{
			for (int j = 0; j <= 3; j++)
			{
				for(int k = 0; k <= 3; k++)
				{
					if(Board_Panel[i][j][k] == '-')
					{
						//Creating a new move on every empty position
						next_pointer = new Board_Movement();
						next_pointer.Board_dimension = i;
						next_pointer.Board_row = j;
						next_pointer.Board_column = k;

						if(Win_Meth(CPU_Selection, next_pointer))
						{
							//Leave the piece there if it is a win and end the game
							Board_Panel[i][j][k] = CPU_Selection;
							button_access[i][j][k].setText(Character.toString(CPU_Selection));
							title.setText("   I win! Press New Game to play again.");
							title.setForeground(Color.RED);
							won = true;
							Score_Y++;

							BoardPanel_game_stop();
							score_BoardPanel();
							break check;
						}
						else
						{
							//This is where the method generates all the possible count moves potentially made
							//by the human player
							if(Difficultlevel != 1)
							{
								human_points = possible_movements(Human_Selection, -1000, 1000);
							}
							else
							{
								//If the player is on easy, just calculate the heuristic value for every current possible move, no looking ahead
								human_points = compatible();
							}

							count = 0;

							//CPU chooses the best hValue out of every move
							if(human_points >= winning_points)
							{
								winning_points = human_points;
								win_BoardPanel = i;
								win_row = j;
								win_column = k;
								Board_Panel[i][j][k] = '-';
							}
							else
							{
								Board_Panel[i][j][k] = '-';
							}
						}
					}
				}
			}
		}

		//If there is no possible winning move, make the move in the calculated best position.
		if(!won)
		{
			Board_Panel[win_BoardPanel][win_row][win_column] = CPU_Selection;
			button_access[win_BoardPanel][win_row][win_column].setText(Character.toString(CPU_Selection));

			button_access[win_BoardPanel][win_row][win_column].setEnabled(false);
		}
	}

	/*
	 * lookAhead() generates all the possible moves in the available spaces based on the current BoardPanel in response
	 * to the possible move made by the computer in computerPlays(). This method returns a heuristic value that is calculated
	 * using the heuristic() function. This function also implements the alpha beta pruning technique since the search
	 * tree can become quite large when playing on hard difficulty
	 */
	private int possible_movements(char c, int a, int b)
	{
		//Alpha and beta values that get passed in
		int alpha = a;
		int beta = b;

		//If you still want to look ahead
		if(count <= Lookahead)
		{

			count++;
			//If you are going to be placing the computer's piece this time
			if(c == CPU_Selection)
			{
				int human_point_1;
				Board_Movement next_pointer_1;

				for (int i = 0; i <= 3; i++)
				{
					for (int j = 0; j <= 3; j++)
					{
						for(int k = 0; k <= 3; k++)
						{
							if(Board_Panel[i][j][k] == '-')
							{
								next_pointer_1 = new Board_Movement();
								next_pointer_1.Board_dimension = i;
								next_pointer_1.Board_row = j;
								next_pointer_1.Board_column = k;

								if(Win_Meth(CPU_Selection, next_pointer_1))
								{
									Board_Panel[i][j][k] = '-';
									return 1000;
								}
								else
								{
									//Recursive look ahead, placing human pieces next
									human_point_1 = possible_movements(Human_Selection, alpha, beta);
									if(human_point_1 > alpha)
									{
										alpha = human_point_1;
										Board_Panel[i][j][k] = '-';
									}
									else
									{
										Board_Panel[i][j][k] = '-';
									}
								}

								//Break out of the look if the alpha value is larger than the beta value, going down no further
								if (alpha >= beta)
									break;
							}
						}
					}
				}

				return alpha;
			}

			//If you are going to be placing the human's piece this time
			else
			{
				int human_point;
				Board_Movement next_pointer;

				for (int i = 0; i <= 3; i++)
				{
					for (int j = 0; j <= 3; j++)
					{
						for(int k = 0; k <= 3; k++)
						{

							if(Board_Panel[i][j][k] == '-')
							{

								next_pointer = new Board_Movement();
								next_pointer.Board_dimension = i;
								next_pointer.Board_row = j;
								next_pointer.Board_column = k;

								if(Win_Meth(Human_Selection, next_pointer))
								{
									Board_Panel[i][j][k] = '-';
									return -1000;
								}
								else
								{
									//Recursive look ahead, placing computer pieces next
									human_point = possible_movements(CPU_Selection, alpha, beta);
									if(human_point < beta)
									{
										beta = human_point;
										Board_Panel[i][j][k] = '-';
									}
									else
									{
										Board_Panel[i][j][k] = '-';
									}
								}

								//Break out of the look if the alpha value is larger than the beta value, going down no further
								if (alpha >= beta)
									break;
							}
						}
					}
				}

				return beta;
			}
		}
		//If you are at the last level of nodes you want to check
		else
		{
			return compatible();
		}
	}

	/*
	 * heuristic() simply uses the  Positions_for_BoardPanel method for both the computer and human on the current BoardPanel, and subtracts them
	 * making a higher value more promising for the computer.
	 */
	private int compatible()
	{
		return (Positions_for_BoardPanel(CPU_Selection) - Positions_for_BoardPanel(Human_Selection));
	}

	/*
	 * Win_Meth() takes in a character that will be checked for a win and a move that checks if that creates a win. It uses
	 * a 2-dimensional array that holds every possible winning combination and a 1-dimensional array that represents all the
	 * spaces on the game BoardPanel.
	 */
	private boolean Win_Meth(char c, Board_Movement pos)
	{
		Board_Panel[pos.Board_dimension][pos.Board_row][pos.Board_column] = c;

		//Win table
		int[][] wins_pos_set = {
				{0, 1, 2,3}, {4, 5,6,7}, {8,9,10,11}, {12, 13, 14,15},
				 {16, 17,18,19},{20,21, 22, 23}, {24, 25, 26,27},{28, 29, 30,31},
				 {32, 33, 34,35},{36, 37, 38,39},{40, 41, 42,43},{44, 45, 46,47},
				 {48, 49, 50,51},{52, 53, 54,55},{56, 57, 58,59},{60, 61, 62,63},

				//Columns on single BoardPanel
				{0,4,8,12}, {1, 5,9,13}, {2,6,10,14}, {3, 7, 11,15}, 
				{16, 20,24,28},{17,21, 25, 29}, {18, 22, 26,30},{19, 23, 27,31},
				{32, 36, 40,44},{33, 37, 41,45},{34, 38, 42,46},{35, 39, 43,47},
				{48, 52, 56,60},{49, 53, 57,61},{50, 54, 58,62},{51, 55, 59,63},
				

				//Diagonals on single BoardPanel
				{0, 5, 10,15}, {3, 6,9,12}, 
				{16,21,26,31}, {19, 22, 25,28}, 
				{32, 37,42,47},{35,38, 41, 44}, 
				{48, 53, 58,63},{51, 54, 57,60},
			
			

				//Straight down through BoardPanels
				{0, 16, 32,48}, {1, 17,33,49}, {2,18,34,50}, {3, 19, 35,51}, 
				{4, 20,36,52},{5,21, 37, 53}, {6, 22, 38,54},{7, 23, 39,55},
				{8, 24, 40,56},{9, 25, 41,57},{10, 26, 42,58},{11, 27, 43,59},
				{12, 28, 44,60},{13, 29, 45,61},{14, 30, 46,62},{15, 31, 47,63},
		

				//Diagonals through BoardPanels
				{0, 20, 40,60}, {1, 21,41,61}, {2,22,42,62}, {3, 23, 43,63}, 
				{12, 24,36,48},{13,25, 37, 49}, {14, 26, 38,50},{15, 27, 39,51},
				{4, 21, 38,55},{8, 25, 42,59},{7, 22, 37,52},{11, 26, 41,56},
				{0, 17, 34,51},{3, 18, 33,48},{12, 29, 46,63},{15, 30, 45,60},
				{0, 21, 42,63},{3, 22, 41,60},{12, 25, 38,51},{15, 26, 37,48},
			 
		};

		//Array that indicates all the spaces on the game BoardPanel
		int[] gameBoardPanel = new int[64];

		//count from 0 to 49, one for each Win_Combo
		int count = 0;

		//If the space on the BoardPanel is the same as the input char, set the corresponding location
		//in gameBoardPanel to 1.
		for (int i = 0; i <= 3; i++)
		{
			for (int j = 0; j <= 3; j++)
			{
				for(int k = 0; k <= 3; k++)
				{
					if(Board_Panel[i][j][k] == c)
					{
						gameBoardPanel[count] = 1;
					}
					else
					{
						gameBoardPanel[count] = 0;
					}
					count++;
				}
			}
		}

		//For each possible Win_Combination
		for (int i = 0; i <= 75; i++)
		{
			//Resetting count to see if all 3 locations have been used
			count = 0;
			for (int j = 0; j <= 3; j++)
			{
				//For each individual winning space in the current combination
				if(gameBoardPanel[wins_pos_set[i][j]] == 1)
				{
					count++;

					Win_Comb[j] = wins_pos_set[i][j];
					//If all 3 moves of the current winning combination are occupied by char c
					if(count == 4)
					{
						return true;
					}
				}
			}
		}

		return false;
	}


	/*
	 * Positions_for_BoardPanel is very similar to wincount(), however instead of returning a boolean if the input
	 * move is a win or not, this method returns an int corresponding to the amount of possible wins available
	 * on the current BoardPanel for input char c ('X' or 'O')
	 */
	private int Positions_for_BoardPanel(char c)
	{
		int wincount = 0;

		//Win table
		int[][] wins = {
				//Rows on single BoardPanel
				{0, 1, 2,3}, {4, 5,6,7}, {8,9,10,11}, {12, 13, 14,15},
				 {16, 17,18,19},{20,21, 22, 23}, {24, 25, 26,27},{28, 29, 30,31},
				 {32, 33, 34,35},{36, 37, 38,39},{40, 41, 42,43},{44, 45, 46,47},
				 {48, 49, 50,51},{52, 53, 54,55},{56, 57, 58,59},{60, 61, 62,63},

				//Columns on single BoardPanel
				{0,4,8,12}, {1, 5,9,13}, {2,6,10,14}, {3, 7, 11,15}, 
				{16, 20,24,28},{17,21, 25, 29}, {18, 22, 26,30},{19, 23, 27,31},
				{32, 36, 40,44},{33, 37, 41,45},{34, 38, 42,46},{35, 39, 43,47},
				{48, 52, 56,60},{49, 53, 57,61},{50, 54, 58,62},{51, 55, 59,63},
				

				//Diagonals on single BoardPanel
				{0, 5, 10,15}, {3, 6,9,12}, 
				{16,21,26,31}, {19, 22, 25,28}, 
				{32, 37,42,47},{35,38, 41, 44}, 
				{48, 53, 58,63},{51, 54, 57,60},
			
				

				//Straight down through BoardPanels
				{0, 16, 32,48}, {1, 17,33,49}, {2,18,34,50}, {3, 19, 35,51}, 
				{4, 20,36,52},{5,21, 37, 53}, {6, 22, 38,54},{7, 23, 39,55},
				{8, 24, 40,56},{9, 25, 41,57},{10, 26, 42,58},{11, 27, 43,59},
				{12, 28, 44,60},{13, 29, 45,61},{14, 30, 46,62},{15, 31, 47,63},
			

				//Diagonals through BoardPanels
				{0, 20, 40,60}, {1, 21,41,61}, {2,22,42,62}, {3, 23, 43,63}, 
				{12, 24,36,48},{13,25, 37, 49}, {14, 26, 38,50},{15, 27, 39,51},
				{4, 21, 38,55},{8, 25, 42,59},{7, 22, 37,52},{11, 26, 41,56},
				{0, 17, 34,51},{3, 18, 33,48},{12, 29, 46,63},{15, 30, 45,60},
				{0, 21, 42,63},{3, 22, 41,60},{12, 25, 38,51},{15, 26, 37,48},
				 
		};

		//Array that indicates all the spaces on the game BoardPanel
		int[] BoardPanel_size = new int[64];

		//count from 0 to 49, one for each Win_Combo
		int count_1 = 0;

		//If the space on the BoardPanel is the same as the input char, set the corresponding location
		//in gameBoardPanel to 1.
		for (int i = 0; i <= 3; i++)
		{
			for (int j = 0; j <= 3; j++)
			{
				for(int k = 0; k <= 3; k++)
				{
					if(Board_Panel[i][j][k] == c || Board_Panel[i][j][k] == '-')
						BoardPanel_size[count_1] = 1;
					else
						BoardPanel_size[count_1] = 0;

					count_1++;
				}
			}
		}

		//For each possible Win_Combination
		for (int i = 0; i <= 75; i++)
		{
			//Resetting count to see if all 3 locations have been used
			count_1 = 0;
			for (int j = 0; j <= 3; j++)
			{
				//For each individual winning space in the current combination
				if(BoardPanel_size[wins[i][j]] == 1)
				{
					count_1++;

					Win_Comb[j] = wins[i][j];
					//If all 3 moves of the current winning combination are occupied by char c
					if(count_1 == 4)
						wincount++;
				}
			}
		}

		return wincount;
	}
}