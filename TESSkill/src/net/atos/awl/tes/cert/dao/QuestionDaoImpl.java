package net.atos.awl.tes.cert.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.atos.awl.tes.cert.vo.LevelExam;
import net.atos.awl.tes.cert.vo.QuestionVo;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

public class QuestionDaoImpl implements QuestionDao {

	@Override
	public int checkPaperAlreadyGiven(String technology, String dasId, int level) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Integer> getAvailableLevel(String technology, String dasId) {

		Connection con = null;
		PreparedStatement stmt = null;
		List<Integer> level = null;
		try {
			con = DBUtils.getConnection();

			stmt = con.prepareStatement(DBUtils.getQuery("level.query"));
			stmt.setString(1, technology);
			stmt.setString(2, dasId);
			ResultSet rs = stmt.executeQuery();
			level = new ArrayList<Integer>(rs.getFetchSize());
			while (rs.next()) {
				level.add(rs.getInt("level"));

			}

		} catch (Exception e) {
			System.out.println("exception " + e.getMessage());
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception ex) {
				System.out.println("exception on DB closing ");
			}
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException logOrIgnore) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException logOrIgnore) {
				}
			}
		}
		return level;

	}

	@Override
	public List<QuestionVo> getQuestionList(String technology, int level, int type) {
		Connection con = null;
		PreparedStatement stmt = null;
		List<QuestionVo> questionList = null;

		try {

			con = DBUtils.getConnection();
			stmt = con.prepareStatement(DBUtils.getQuery("questionList.query"));
			stmt.setString(1, technology);
			stmt.setInt(2, level);
			stmt.setInt(3, type);
			// stmt.setFetchSize(6);
			LevelExam levelExam = getQuestionCount(technology, level);
			if (type == 0) {
				stmt.setMaxRows(levelExam.getQuestionCount());
			} else {
				stmt.setMaxRows(levelExam.getDescriptiveQuestionCount());
			}

			ResultSet rs = stmt.executeQuery();
			questionList = new ArrayList<QuestionVo>();
			// questionList = new ArrayList<QuestionVo>(rs.getFetchSize());
			while (rs.next()) {
				QuestionVo quesVo = new QuestionVo();
				quesVo.setQuestionId(rs.getInt("question_Id"));
				quesVo.setExamId(rs.getString("exam_id"));
				quesVo.setOption1(rs.getString("option1"));
				quesVo.setOption2(rs.getString("option2"));
				quesVo.setOption3(rs.getString("option3"));
				quesVo.setOption4(rs.getString("option4"));
				quesVo.setCorrectAnswer(rs.getInt("correct_option"));
				quesVo.setQuestionText(getDecodedHtmlValue(rs.getString("text")));
				quesVo.setHint(rs.getString("description") != null ? rs.getString("description") : "NA");
				questionList.add(quesVo);
			}
			System.out.println("questionList size in DAO " + questionList.size());
		} catch (Exception e) {
			System.out.println("exception " + e.getMessage());
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception ex) {
				System.out.println("exception on DB closing ");
			}
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException logOrIgnore) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException logOrIgnore) {
				}
			}
		}
		return questionList;
	}

	@Override
	public List<QuestionVo> getRemainingQuestionList(Integer resultId, int examId) {
		Connection con = null;
		PreparedStatement stmt = null;
		List<QuestionVo> questionList = new ArrayList<QuestionVo>();

		try {
			Integer attemptedQuestion = getUserAttemptedQuestionCount(resultId);
			LevelExam levelExam = getQuestionCountByExamId(examId);
			Integer count = levelExam.getQuestionCount() - attemptedQuestion;
			System.out.println("getRemainingQuestionList DAO " + count);
			if (count>0)
			{
				con = DBUtils.getConnection();
				stmt = con.prepareStatement(DBUtils.getQuery("user.remaining.questionList"));
				// stmt.setFetchSize(6);
				stmt.setInt(1, examId);
				stmt.setInt(2, resultId);
				stmt.setInt(3, 0);
				stmt.setMaxRows(count);
				ResultSet rs = stmt.executeQuery();
				while (rs.next())
				{
					QuestionVo quesVo = new QuestionVo();
					quesVo.setQuestionId(rs.getInt("question_Id"));
					quesVo.setExamId(rs.getString("exam_id"));
					quesVo.setOption1(rs.getString("option1"));
					quesVo.setOption2(rs.getString("option2"));
					quesVo.setOption3(rs.getString("option3"));
					quesVo.setOption4(rs.getString("option4"));
					quesVo.setCorrectAnswer(rs.getInt("correct_option"));
					quesVo.setQuestionText(getDecodedHtmlValue(rs.getString("text")));
					questionList.add(quesVo);
				}
				System.out.println("questionList size in DAO " + questionList.size());
			}
		} catch (Exception e) {
			System.out.println("exception " + e.getMessage());
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception ex) {
				System.out.println("exception on DB closing ");
			}
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException logOrIgnore) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException logOrIgnore) {
				}
			}
		}
		return questionList;
	}

	@Override
	public List<QuestionVo> getRemainingDesQuestionList(Integer resultId, int examId) {
		Connection con = null;
		PreparedStatement stmt = null;
		List<QuestionVo> questionList = new ArrayList<QuestionVo>();

		try {
			Integer attemptedQuestion = getUserAttemptedDescQuestionCount(resultId);
			LevelExam levelExam = getQuestionCountByExamId(examId);
			Integer descCount = levelExam.getDescriptiveQuestionCount() - attemptedQuestion;
			System.out.println("getRemainingQuestionList DAO " + descCount);
			if(descCount > 0)
			{
				con = DBUtils.getConnection();
				stmt = con.prepareStatement(DBUtils.getQuery("user.remaining.questionList"));
				// stmt.setFetchSize(6);
				stmt.setInt(1, examId);
				stmt.setInt(2, resultId);
				stmt.setInt(3, 1);
				stmt.setMaxRows(descCount);
				ResultSet rs = stmt.executeQuery();

				// questionList = new ArrayList<QuestionVo>(rs.getFetchSize());
				while (rs.next()) {
					QuestionVo quesVo = new QuestionVo();
					quesVo.setQuestionId(rs.getInt("question_Id"));
					quesVo.setExamId(rs.getString("exam_id"));
					quesVo.setOption1(rs.getString("option1"));
					quesVo.setOption2(rs.getString("option2"));
					quesVo.setOption3(rs.getString("option3"));
					quesVo.setOption4(rs.getString("option4"));
					quesVo.setCorrectAnswer(rs.getInt("correct_option"));
					quesVo.setQuestionText(getDecodedHtmlValue(rs.getString("text")));
					questionList.add(quesVo);
				}
				System.out.println("questionList size in DAO " + questionList.size());
			}
		} catch (Exception e) {
			System.out.println("exception " + e.getMessage());
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception ex) {
				System.out.println("exception on DB closing ");
			}
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException logOrIgnore) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException logOrIgnore) {
				}
			}
		}
		return questionList;
	}

	@Override
	public int getUserAnswerQuestionCount(Integer resultId) {
		int answeredQuestion = 0;
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = DBUtils.getConnection();
			stmt = con
					.prepareStatement("select count(*) as ansQuestion from result_details where answer_option = '0' and result_id = ?");
			stmt.setInt(1, resultId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				answeredQuestion = rs.getInt("ansQuestion");
			}
			rs.close();

		} catch (Exception e) {
			System.out.println("exception " + e.getMessage());
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception ex) {
				System.out.println("exception on DB closing ");
			}
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException logOrIgnore) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException logOrIgnore) {
				}
			}
		}
		return answeredQuestion;
	}

	@Override
	public int getUserAttemptedQuestionCount(Integer resultId) {

		Connection con = null;
		PreparedStatement stmt = null;
		Integer questinAttemptedCount = 0;
		try {
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(DBUtils.getQuery("user.result.details.history.count"));
			stmt.setInt(1, resultId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				questinAttemptedCount = rs.getInt("rowcount");
			}
			rs.close();

		} catch (Exception e) {
			System.out.println("exception " + e.getMessage());
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception ex) {
				System.out.println("exception on DB closing ");
			}
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException logOrIgnore) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException logOrIgnore) {
				}
			}
		}

		System.out.println("return of value of attempted question count" + questinAttemptedCount);
		return questinAttemptedCount;
	}

	@Override
	public int getUserAttemptedDescQuestionCount(Integer resultId) {

		Connection con = null;
		PreparedStatement stmt = null;
		Integer questinAttemptedCount = 0;
		try {
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(DBUtils.getQuery("user.result.details.desc.history.count"));
			stmt.setInt(1, resultId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				questinAttemptedCount = rs.getInt("rowcount");
			}
			rs.close();

		} catch (Exception e) {
			System.out.println("exception " + e.getMessage());
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception ex) {
				System.out.println("exception on DB closing ");
			}
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException logOrIgnore) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException logOrIgnore) {
				}
			}
		}

		System.out.println("return of value of attempted descriptive count " + questinAttemptedCount);
		return questinAttemptedCount;
	}

	@Override
	public int getUserAttemptedDescQuestionList(Integer resultId) {

		Connection con = null;
		PreparedStatement stmt = null;
		Integer answeredQuestins = 0;
		try {
			con = DBUtils.getConnection();
			String query = DBUtils.getQuery("user.result.desc.attempted.question.count");
			// System.out.println("query before execution of descriptive answered questions " + query);
			stmt = con.prepareStatement(query);
			stmt.setInt(1, resultId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				answeredQuestins = rs.getInt("descCount");
			}
			rs.close();

		} catch (Exception e) {
			System.out.println("exception " + e.getMessage());
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception ex) {
				System.out.println("exception on DB closing ");
			}
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException logOrIgnore) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException logOrIgnore) {
				}
			}
		}

		System.out.println("return of value of not answeredQuestins count" + answeredQuestins);
		return answeredQuestins;
	}


	@Override
	public LevelExam getQuestionCountByExamId(int examId) {

		Connection con = null;
		PreparedStatement stmt = null;
		LevelExam levelExam = null;
		try {
			con = DBUtils.getConnection();

			stmt = con.prepareStatement(DBUtils.getQuery("question.count.by.exam.id"));
			stmt.setInt(1, examId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				levelExam = new LevelExam();
				levelExam.setQuestionCount(rs.getInt("question_count"));
				levelExam.setPassingCount(rs.getInt("passing_count"));
				levelExam.setDescriptiveQuestionCount(rs.getInt("descriptive_question_count"));
				// levelExam.setDescriptivePassingCount(rs.getInt("descriptive_passing_count"));
			}

		} catch (Exception e) {
			System.out.println("exception " + e.getMessage());
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception ex) {
				System.out.println("exception on DB closing ");
			}
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException logOrIgnore) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException logOrIgnore) {
				}
			}
		}
		// System.out.println("getQuestionCountByExamId DescriptiveQuestionCount"
		// + levelExam.getDescriptiveQuestionCount());
		return levelExam;
	}

	private LevelExam getQuestionCount(String technology, int level) {

		Connection con = null;
		PreparedStatement stmt = null;
		LevelExam levelExam = null;
		try {
			con = DBUtils.getConnection();

			stmt = con.prepareStatement(DBUtils.getQuery("question.count"));
			stmt.setString(1, technology);
			stmt.setInt(2, level);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				levelExam = new LevelExam();
				levelExam.setDescriptiveQuestionCount(rs.getInt("descriptive_question_count"));
				levelExam.setQuestionCount(rs.getInt("question_count"));
			}

		} catch (Exception e) {
			System.out.println("exception " + e.getMessage());
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception ex) {
				System.out.println("exception on DB closing ");
			}
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException logOrIgnore) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException logOrIgnore) {
				}
			}
		}
		return levelExam;
	}
	@Override
	public List<QuestionVo> getAttemptedDescQuestionList(Integer resultId) {

		Connection con = null;
		PreparedStatement stmt = null;
		List<QuestionVo> questionList = new ArrayList<QuestionVo>();
		try {
			con = DBUtils.getConnection();
			String query = DBUtils.getQuery("attempted.des.question.list");
			System.out.println("query before execution of descriptive answered questions " + query);
			stmt = con.prepareStatement(query);
			stmt.setInt(1, resultId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				QuestionVo question = new QuestionVo();
				question.setQuestionText(getDecodedHtmlValue(rs.getString("text")));
				question.setQuestionId(rs.getInt("question_id"));
				question.setSelectedAnswer(rs.getString("answer_option"));
				questionList.add(question);
			}
			rs.close();

		} catch (Exception e) {
			System.out.println("exception " + e.getMessage());
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception ex) {
				System.out.println("exception on DB closing ");
			}
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException logOrIgnore) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException logOrIgnore) {
				}
			}
		}

		return questionList;
	}

	@Override
	public List<QuestionVo> getQuestions(Integer resultId)
	{

		Connection con = null;
		PreparedStatement stmt = null;
		List<QuestionVo> questionList = new ArrayList<QuestionVo>();
		try {
			con = DBUtils.getConnection();
			String query = DBUtils.getQuery("question.list");
			stmt = con.prepareStatement(query);
			stmt.setInt(1, resultId);

			System.out.println("query for wrong answered questions " + stmt.toString());

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				QuestionVo question = new QuestionVo();
				List<String> op = new ArrayList<String>();
				question.setQuestionText(getDecodedHtmlValue(rs.getString("text")));
				question.setQuestionId(rs.getInt("question_id"));
				question.setOption1(rs.getString("option1"));
				question.setOption2(rs.getString("option2"));
				question.setOption3(rs.getString("option3"));
				question.setOption4(rs.getString("option4"));

				op.add(rs.getString("option1"));
				op.add(rs.getString("option2"));
				op.add(rs.getString("option3"));
				op.add(rs.getString("option4"));

				question.setOptions(op);

				question.setQuestionType(rs.getString("question_type"));
				question.setSelectedAnswer(rs.getString("answer_option"));

				questionList.add(question);
			}
			rs.close();

		} catch (Exception e) {
			System.out.println("exception " + e.getMessage());
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception ex) {
				System.out.println("exception on DB closing ");
			}
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException logOrIgnore) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException logOrIgnore) {
				}
			}
		}

		return questionList;

	}

	//This method is to transform all html tag in it's html value so that on page its displayed properly with escape = false
	//Keep BR in html format to get actual line break
	private String getDecodedHtmlValue(String encodedStr)
	{
		String decodedStr= new String(Base64.decodeBase64(encodedStr));
		decodedStr=decodedStr.replace("&", "&amp;");
		decodedStr=decodedStr.replace("<", "&lt;");
		decodedStr=decodedStr.replace(">", "&gt;");
		decodedStr=decodedStr.replace("\n", "<BR>");
		return decodedStr;
	}
}
