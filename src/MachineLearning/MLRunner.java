package MachineLearning;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import Model.MLThirtyFiveRepBoard;
import Model.Move;
import Model.ThirtyFiveRepCheckerPiece;

public class MLRunner {
	
	public static void main(String[] args) throws IOException
	{
		for(int i = 0; i < 50; i++)
		{
			//Initialize all the tools we need
			//ReGenerateWeights("Black");
			//ReGenerateWeights("White");
			int movestaken = 0;
			int maxmoves = 100;
			ThirtyFiveRepCheckerPiece[] set = new ThirtyFiveRepCheckerPiece[35];
			MLControllerConstants p1const = new MLControllerConstants("Player1const.txt");
			MLWeightConstants p1weights = new MLWeightConstants("Player1weight.txt");
			MLControllerConstants p2const = new MLControllerConstants("Player2const.txt");
			MLWeightConstants p2weights = new MLWeightConstants("Player2weight.txt");
			System.out.println("ALPHABETA TEST");
			MLAlphaBeta Tester = new MLAlphaBeta(p1const, p1weights, p2const, p2weights);
			MLThirtyFiveRepBoard Board = new MLThirtyFiveRepBoard("Black", set);
			Board.initBoard();
			while(!Board.isTerminal() && movestaken<maxmoves)
			{
				movestaken++;
				//System.out.println("**********************************");
				double start = System.nanoTime();
				Board =  (MLThirtyFiveRepBoard) Board.result(Tester.alphabeta(Board, 9));
				double end = System.nanoTime();
				//Board.printState();				
				//System.out.println("Time taken: " + (end - start) / 1000000 + "ms");
			}
			System.out.println(Board.player());
			Board.printState();
			if(movestaken != maxmoves)
			{
				if(Board.player().equals("Black"))
				{
					ReGenerateWeights("Black");
				}
				else
				{
					ReGenerateWeights("White");
				}
			}
			else
			{
				if(Board.WhoHasMorePieces().equals("Black"))
				{
					System.out.println("We are regenerating: White");
					ReGenerateWeights("White");
				}
				else if (Board.WhoHasMorePieces().equals("White"))
				{
					System.out.println("We are regenerating: Black");
					ReGenerateWeights("Black");
				}
			}
		}
	}
	
	public static void ReGenerateWeights(String winner) throws IOException
	{
		Random rand = new Random();
		ArrayList<String[]> LineList = new ArrayList<String[]>();
		String line = "";
		String towrite = "";
		if(winner.equals("Black"))
		{
			BufferedReader MyReader = new BufferedReader(new FileReader(new File("Player1weight.txt")));
			while((line = MyReader.readLine()) != null)
			{
				//System.out.println(line);
				LineList.add(line.split(" "));
			}
			MyReader.close();
			BufferedWriter MyWriter = new BufferedWriter(new FileWriter(new File("Player1weight.txt")));
			for(int i = 0; i < LineList.size(); i++)
			{
				LineList.get(i)[2] = Integer.toString((rand.nextInt(11) - 5));
				towrite = LineList.get(i)[0] + " " + LineList.get(i)[1] + " " + LineList.get(i)[2];
				//System.out.println(towrite);
				MyWriter.write(towrite);
				MyWriter.newLine();
			}
			MyWriter.flush();
			MyWriter.close();
		}
		else
		{
			BufferedReader MyReader = new BufferedReader(new FileReader(new File("Player2weight.txt")));
			while((line = MyReader.readLine()) != null)
			{
				//System.out.println(line);
				LineList.add(line.split(" "));
			}
			MyReader.close();
			BufferedWriter MyWriter = new BufferedWriter(new FileWriter(new File("Player2weight.txt")));
			for(int i = 0; i < LineList.size(); i++)
			{
				LineList.get(i)[2] = Integer.toString((rand.nextInt(11) - 5));
				towrite = LineList.get(i)[0] + " " + LineList.get(i)[1] + " " + LineList.get(i)[2];
				//System.out.println(towrite);
				MyWriter.write(towrite);
				MyWriter.newLine();
			}
			MyWriter.flush();
			MyWriter.close();
		}
	}
}
