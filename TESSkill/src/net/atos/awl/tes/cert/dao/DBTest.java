package net.atos.awl.tes.cert.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class DBTest {
	public static void main(String args[]) {
		
		String url = "jdbc:mysql://10.87.166.12:3306/";
		//String url = "jdbc:mysql://localhost:3306/";
		String dbName = "tescert";
		String driverName = "com.mysql.jdbc.Driver";
		String userName =  "certexam"; // "root"; //           
		String password = "mysql";
		Connection con = null;
		Statement stmt = null;
		PreparedStatement ps = null;
		try {

			if(args.length==0)
			{
				printHelp();
				return;
			}
			Class.forName(driverName).newInstance();
			con = DriverManager.getConnection(url + dbName, userName, password);
			
			stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("select level,exam_id from level_exam where tech_id="+args[1]);
			
			Map<Integer,Integer> map = new HashMap<Integer,Integer>();
			
			while(rs.next())
			{
				map.put(rs.getInt(1), rs.getInt(2));
			}
			
			System.out.println("map = "+map);
			
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(args[0]));
			HSSFSheet sheet = workbook.getSheetAt(0);
			int rowCount = sheet.getLastRowNum();
			
			workbook.setMissingCellPolicy(HSSFRow.CREATE_NULL_AS_BLANK);
			
			int startRow = Integer.parseInt(args[2]);
			System.out.println("row count = "+rowCount + " startRow "+startRow);
			
			ps = con.prepareStatement("insert  into `questions`(`text`,`option1`,`option2`,`option3`,`option4`,`question_type`,`correct_option`,`exam_id`) values (?,?,?,?,?,?,?,?)");
			for(int i=startRow;i<=rowCount;i++)
			{
				System.out.println("processing row "+i);
				HSSFRow row = sheet.getRow(i);
				
				String question = row.getCell(1).getRichStringCellValue().getString();
				if(question==null || question.trim().length()==0)
					continue;
				
				int level = (NumberFormat.getIntegerInstance()).parse(Double.toString(row.getCell(2).getNumericCellValue())).intValue();
				
				System.out.println("Level is "+level + " from map exam id "+map.get(level));
				System.out.println(row.getCell(2).getCellType()+" cell type "+row.getCell(3).getCellType()+" cell type "+row.getCell(7));
				
				String option1 = row.getCell(3).getCellType()==HSSFCell.CELL_TYPE_NUMERIC?String.valueOf(row.getCell(3).getNumericCellValue()):row.getCell(3).getRichStringCellValue().getString();
				String option2 = row.getCell(4).getCellType()==HSSFCell.CELL_TYPE_NUMERIC?String.valueOf(row.getCell(4).getNumericCellValue()):row.getCell(4).getRichStringCellValue().getString();
				String option3 = row.getCell(5).getCellType()==HSSFCell.CELL_TYPE_NUMERIC?String.valueOf(row.getCell(5).getNumericCellValue()):row.getCell(5).getRichStringCellValue().getString();
				String option4 = row.getCell(6).getCellType()==HSSFCell.CELL_TYPE_NUMERIC?String.valueOf(row.getCell(6).getNumericCellValue()):row.getCell(6).getRichStringCellValue().getString();
			//	String option5 = row.getCell(7).getCellType()==HSSFCell.CELL_TYPE_NUMERIC?String.valueOf(row.getCell(7).getNumericCellValue()):row.getCell(7).getRichStringCellValue().getString();
				int type = "Subjective".equalsIgnoreCase(row.getCell(7).getRichStringCellValue().getString())?1:0;
				
				//correct option only in case of objective
				int correctOption = type==1?0:(NumberFormat.getIntegerInstance()).parse(Double.toString(row.getCell(8).getNumericCellValue())).intValue();
				
				ps.setString(1, Base64.encodeBase64String(question.getBytes())); // decode using - decoded = new String(Base64.decodeBase64(encoded));
				ps.setString(2, option1==null?null:option1.trim());
				ps.setString(3, option2==null?null:option2.trim());
				ps.setString(4, option3==null?null:option3.trim());
				ps.setString(5, option4==null?null:option4.trim());
			//	ps.setString(6, option5==null?null:option5.trim());
				ps.setInt(6, type);
				ps.setInt(7, correctOption);
				ps.setInt(8, map.get(level));
				
				System.out.println("Final query is "+ps.toString());
				int val = ps.executeUpdate();
				if (val == 0) {
					System.out.println("Failure");
				} else {
					System.out.println("Success");
				}
				ps.clearParameters();
			}	
			
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Got exception "+e.getMessage());
		}
		finally
		{
			if(ps != null)
				try
				{
					ps.close();
				} catch (SQLException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("Got exception "+e.getMessage());
				}
		}
		
		System.out.println("#### Import completed ####");
	}
	
	private static void printHelp()
	{
		System.out.println("Call java DBTest <xls File path> <techid as per db> <start row in file - 0 starts>");
	}
}
