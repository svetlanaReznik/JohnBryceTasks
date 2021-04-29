package beanUtil;

import java.util.List;

public class TableGenerator {
	private static final int PADDING_SIZE = 4;
	private static final String NEW_LINE = "\n";
	private static final String TABLE_JOINT_SYMBOL = "+";
	private static final String TABLE_V_SPLIT_SYMBOL = "|";
	private static final String TABLE_H_SPLIT_SYMBOL = "-";
	private static int MAX_WORD_WIDTH = 20;
	private static int MAX_COLUMN_WIDTH = MAX_WORD_WIDTH + 2*PADDING_SIZE;
	
	public static void printHeader(List<String> headersList) 
	{
		StringBuilder stringBuilder = new StringBuilder();
		createLine(headersList, stringBuilder);
		
		for (int i = 0; i < headersList.size(); i++) {
			fillCell(stringBuilder, headersList.get(i), i);
		}
		createLine(headersList, stringBuilder);
		System.out.print(stringBuilder.toString());
	}

	public static void printRow(List<String> headersList, List<String> dataList) 
	{
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < dataList.size(); i++) {
			fillCell(stringBuilder, dataList.get(i), i);
		}
		createLine(headersList, stringBuilder);
		System.out.print(stringBuilder.toString());
	}
	
	private static void fillCell(StringBuilder stringBuilder, String cell, int cellIndex) 
	{
		if(cellIndex == 0){
            stringBuilder.append(TABLE_V_SPLIT_SYMBOL); 
        }
		
		int freeSpace = (MAX_COLUMN_WIDTH - cell.length()) - (2*PADDING_SIZE);
		int preSpace = freeSpace/2 + PADDING_SIZE;
		int postSpace = (freeSpace - freeSpace/2) + PADDING_SIZE;
		
		fillSpace(stringBuilder, preSpace);
		stringBuilder.append(cell);
		fillSpace(stringBuilder, postSpace);
        stringBuilder.append(TABLE_V_SPLIT_SYMBOL); 
	}

	private static void fillSpace(StringBuilder stringBuilder, int space) {
		for (int i = 0; i < space; i++) {
            stringBuilder.append(" ");
        }
	}

	private static void createLine(List<String> headersList, StringBuilder stringBuilder) 
	{
		stringBuilder.append(NEW_LINE);
		for (int i = 0; i < headersList.size(); i++) 
		{
			if(i == 0){
	            stringBuilder.append(TABLE_JOINT_SYMBOL);   
	        }
			
			for (int j = 0; j < MAX_WORD_WIDTH + PADDING_SIZE * 2 ; j++) {
                stringBuilder.append(TABLE_H_SPLIT_SYMBOL);
            }
            stringBuilder.append(TABLE_JOINT_SYMBOL);
        }
		stringBuilder.append(NEW_LINE);
	}

//	private static int getMaximumColumnWidth(List<String> headersList, List<String> rows) 
//	{
//		int maxHeaderWidth = headersList.stream().max(Comparator.comparingInt(String::length)).get().length();
//
//		int maxRowWidth = rows.stream().max(Comparator.comparingInt(String::length)).get().length();
//
//		return (maxRowWidth > maxHeaderWidth) ? maxRowWidth : maxHeaderWidth;
//	}

	public static void print(List<String> headersList, String str) {
		System.out.println();
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(TABLE_JOINT_SYMBOL); 
		
		int width = (MAX_COLUMN_WIDTH * headersList.size())+headersList.size()-1;
		for (int i = 0; i < width; i++) {
			stringBuilder.append(TABLE_H_SPLIT_SYMBOL); 
		}
		
		stringBuilder.append(TABLE_JOINT_SYMBOL); 
		stringBuilder.append(NEW_LINE);
		fillTitle(stringBuilder, str, width);
		System.out.print(stringBuilder.toString());
	}
	
	private static void fillTitle(StringBuilder stringBuilder, String cell, int width) 
	{
		stringBuilder.append(TABLE_V_SPLIT_SYMBOL); 
        		
		int freeSpace = width - cell.length();
		int preSpace = freeSpace/2;
		int postSpace = freeSpace - preSpace;
		
		fillSpace(stringBuilder, preSpace);
		stringBuilder.append(cell);
		fillSpace(stringBuilder, postSpace);
        stringBuilder.append(TABLE_V_SPLIT_SYMBOL); 
	}
}
