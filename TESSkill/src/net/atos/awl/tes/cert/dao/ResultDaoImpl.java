/**
 * 
 */
package net.atos.awl.tes.cert.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.atos.awl.tes.cert.vo.LevelExam;
import net.atos.awl.tes.cert.vo.ManagerReport;
import net.atos.awl.tes.cert.vo.QuestionVo;
import net.atos.awl.tes.cert.vo.Result;
import net.atos.awl.tes.cert.vo.ResultDetails;

/**
 * @author Gau
 * 
 */
public class ResultDaoImpl implements ResultDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.atos.awl.tes.cert.dao.ResultDao#getAllResults(java.lang.String)
	 */
	@Override
	public List<Result> getAllResults(String dasId) {
		Connection con = null;
		PreparedStatement stmt = null;
		List<Result> resultList = null;
		try {
			con = DBUtils.getConnection();

			stmt = con.prepareStatement(DBUtils.getQuery("result.das.all"));
			stmt.setString(1, dasId);
			ResultSet rs = stmt.executeQuery();
			resultList = new ArrayList<Result>(rs.getFetchSize());
			while (rs.next()) {
				Result res = new Result();
				res.setResultId(rs.getInt("result_id"));
				res.setExamId(rs.getInt("exam_id"));
				res.setEnrolledOn(rs.getDate("enrolled_on"));
				res.setSubmittedOn(rs.getDate("submitted_on"));
				if (rs.getInt("result") == 1) {
					res.setIsPass("Pass");
				} else {
					res.setIsPass("Fail");
				}
				res.setPercentile(rs.getDouble("percentile"));
				resultList.add(res);
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
		return resultList;
	}

	@Override
	public int createResult(String dasId, String technology, String examId,
			double percentile, int result) {

		Connection con = null;
		PreparedStatement stmt = null;
		int val = 0;
		try {
			// levelExam = getLevelExamResult(technology, level);
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(DBUtils.getQuery("create.result"));
			stmt.setString(1, examId);
			stmt.setString(2, dasId);
			stmt.setDouble(3, percentile);
			stmt.setInt(4, result);
			val = stmt.executeUpdate();
			if (val == 0) {
				System.out.println("Failure");
			} else {
				System.out.println("Success");
				ResultSet r = stmt.executeQuery("SELECT LAST_INSERT_ID()");
				r.next();
				val = r.getInt(1);
				System.out.println("get id is " + val);
			}
			System.out.println("val:::::" + val);

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

		return val;

	}

	@Override
	public LevelExam getLevelExamResult(String technology, Integer level) {

		LevelExam levelExam = null;
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = DBUtils.getConnection();

			stmt = con.prepareStatement(DBUtils.getQuery("levelExam.query"));
			stmt.setString(1, technology);
			stmt.setInt(2, level);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				levelExam = new LevelExam();
				levelExam.setExamId(rs.getString("exam_id"));
				levelExam.setQuestionCount(rs.getInt("question_count"));
				levelExam.setPassingCount(rs.getInt("passing_count"));
				levelExam.setDescriptiveQuestionCount(rs
						.getInt("descriptive_question_count"));
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
	public Result getResult(int resultId) {
		Result result = null;
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = DBUtils.getConnection();

			stmt = con.prepareStatement(DBUtils.getQuery("result.byresult.id"));
			stmt.setInt(1, resultId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				result = new Result();
				result.setResultId(rs.getInt("result_id"));
				result.setExamId(rs.getInt("exam_id"));
				result.setEnrolledOn(rs.getDate("enrolled_on"));
				result.setSubmittedOn(rs.getDate("submitted_on"));
				if (rs.getInt("result") == 1) {
					result.setIsPass("Pass");
				} else {
					result.setIsPass("Fail");
				}
				result.setPercentile(rs.getDouble("percentile"));
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
		return result;
	}

	@Override
	public Result getResult(String dasId, String technology, Integer level) {

		Result result = null;
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = DBUtils.getConnection();

			stmt = con.prepareStatement(DBUtils.getQuery("result.query"));
			stmt.setString(1, dasId);
			stmt.setString(2, technology);
			stmt.setInt(3, level);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				result = new Result();
				result.setResultId(rs.getInt("result_id"));
				result.setExamId(rs.getInt("exam_id"));
				result.setEnrolledOn(rs.getDate("enrolled_on"));
				result.setSubmittedOn(rs.getDate("submitted_on"));
				if (rs.getInt("result") == 1) {
					result.setIsPass("Pass");
				} else {
					result.setIsPass("Fail");
				}
				result.setPercentile(rs.getDouble("percentile"));
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
		return result;
	}

	@Override
	public List<Result> getReviewResultList(String technology, Integer level) {
		List<Result> rList = new ArrayList<Result>();
		Connection con = null;
		QuestionDao questionDao = new QuestionDaoImpl();
		PreparedStatement stmt = null;
		try {
			con = DBUtils.getConnection();

			stmt = con.prepareStatement(DBUtils.getQuery("review.result.list"));
			stmt.setString(1, technology);
			stmt.setInt(2, level);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Result result = new Result();
				int rId = rs.getInt("result_id");
				List<QuestionVo> qList = questionDao
						.getAttemptedDescQuestionList(rId);
				result.setResultId(rId);
				result.setSubmittedOn(new java.util.Date(rs.getDate(
						"submitted_on").getTime()));
				if (qList.size() > 0) {
					result.setIsPass("Pending");
				} else {
					result.setIsPass("Completed");
				}
				rList.add(result);
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
		return rList;
	}

	@Override
	public Integer getAttemptCount(String dasId, String technology,
			Integer level) {

		Connection con = null;
		PreparedStatement stmt = null;
		Integer resultCount = 0;
		try {
			con = DBUtils.getConnection();

			stmt = con.prepareStatement(DBUtils.getQuery("enorled.count"));
			stmt.setString(1, dasId);
			stmt.setString(2, technology);
			stmt.setInt(3, level);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				resultCount = rs.getInt("rowcount");
				System.out.println("value of result count" + resultCount);
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
		System.out.println("return of value of result count" + resultCount);
		return resultCount;
	}

	@Override
	public boolean searchQuestions(int resultId, List<Integer> qstionIdList) {
		boolean found = false;
		Connection con = null;
		int rowCount = 0;
		PreparedStatement stmt = null;
		try {
			con = DBUtils.getConnection();
			String queryToRun = DBUtils.getQuery("search.questions");
			for (int index = 0; index < (qstionIdList.size() - 1); index++) {
				// if(qstionIdList.size() >= 2){
				queryToRun = queryToRun + ",?";
				// }
			}
			queryToRun = queryToRun + ")";
			// System.out.println("DAO...search query is " + queryToRun);
			stmt = con.prepareStatement(queryToRun);
			stmt.setInt(1, resultId);
			for (int index = 0; index < qstionIdList.size(); index++) {
				stmt.setInt(index + 2, qstionIdList.get(index));
			}
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				rowCount = rs.getInt("rowCount");
			}
			if (rowCount > 0) {
				found = true;
			}
			System.out.println("resultList size " + rowCount);
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
		return found;
	}

	@Override
	public List<ResultDetails> getResultDetails(String resultId) {
		List<ResultDetails> resultList = new ArrayList<ResultDetails>();
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(DBUtils
					.getQuery("select.resultDetails"));
			stmt.setString(1, resultId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				ResultDetails rd = new ResultDetails();
				rd.setResultId(rs.getInt("result_id"));
				rd.setQuestionId(rs.getInt("question_id"));
				rd.setAnswerOption(rs.getString("answer_option"));
				rd.setCorrectOption(rs.getInt("correct_option"));
				resultList.add(rd);
			}
			System.out.println("resultList size " + resultList.size());
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
		return resultList;
	}

	@Override
	public Integer getUnReviewedDescQuestionCount(String resultId) {

		Connection con = null;
		PreparedStatement stmt = null;
		Integer unReviewedDescQuestionCount = 0;
		try {
			con = DBUtils.getConnection();

			stmt = con.prepareStatement(DBUtils
					.getQuery("user.unReviewedDescQuestionCount"));
			stmt.setString(1, resultId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				unReviewedDescQuestionCount = rs.getInt("rowcount");
				System.out.println("value of unReviewed Desc Question Count"
						+ unReviewedDescQuestionCount);
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

		return unReviewedDescQuestionCount;

	}

	@Override
	public List<ResultDetails> getResultDetailsByType(String resultId, int type) {
		List<ResultDetails> resultList = new ArrayList<ResultDetails>();
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = DBUtils.getConnection();
			stmt = con
					.prepareStatement(DBUtils.getQuery("user.result.details"));
			stmt.setString(1, resultId);
			stmt.setInt(2, type);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				ResultDetails rd = new ResultDetails();
				rd.setResultId(rs.getInt("result_id"));
				rd.setQuestionId(rs.getInt("question_id"));
				rd.setAnswerOption(rs.getString("answer_option"));
				rd.setCorrectOption(rs.getInt("correct_option"));
				resultList.add(rd);
			}
			System.out.println("resultList size " + resultList.size());
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
		return resultList;
	}

	@Override
	public void createResultDetails(List<ResultDetails> rsultDetailsList) {

		Connection con = null;
		Statement stmt = null;

		try {
			con = DBUtils.getConnection();
			stmt = con.createStatement();

			for (int i = 0; i < rsultDetailsList.size(); i++) {
				ResultDetails resultDetail = rsultDetailsList.get(i);
				stmt.addBatch("INSERT into result_details (result_id, question_id, answer_option , correct_option) values ("
						+ resultDetail.getResultId()
						+ " ,"
						+ resultDetail.getQuestionId()
						+ ", "
						+ resultDetail.getAnswerOption()
						+ ","
						+ resultDetail.getCorrectOption() + ")");
			}
			int[] res = stmt.executeBatch();
			System.out.println("res output is " + Arrays.asList(res));
		} catch (SQLException e) {
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

	}

	@Override
	public void updateResultDetails(List<ResultDetails> rsultDetailsList) {

		Connection con = null;
		Statement stmt = null;

		try {
			con = DBUtils.getConnection();
			stmt = con.createStatement();

			for (int i = 0; i < rsultDetailsList.size(); i++) {
				ResultDetails resultDetail = rsultDetailsList.get(i);
				stmt.addBatch("update result_details set answer_option = "
						+ resultDetail.getAnswerOption()
						+ " where question_id = "
						+ resultDetail.getQuestionId() + " and result_id = "
						+ resultDetail.getResultId());
			}
			int[] res = stmt.executeBatch();
			System.out.println("res output is " + res[0]);
		} catch (SQLException e) {
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
	}

	@Override
	public void updateResultDetailsForReview(
			List<ResultDetails> rsultDetailsList) {

		Connection con = null;
		Statement stmt = null;

		try {
			con = DBUtils.getConnection();
			stmt = con.createStatement();

			for (int i = 0; i < rsultDetailsList.size(); i++) {
				ResultDetails resultDetail = rsultDetailsList.get(i);
				stmt.addBatch("update result_details set correct_option = "
						+ resultDetail.getCorrectOption()
						+ " where question_id = "
						+ resultDetail.getQuestionId() + " and result_id = "
						+ resultDetail.getResultId());
			}
			int[] res = stmt.executeBatch();
			System.out.println("res output is " + res[0]);
		} catch (SQLException e) {
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
	}

	@Override
	public void updateResultDetails(ResultDetails rsultDetail) {

		Connection con = null;
		PreparedStatement stmt = null;

		try {
			con = DBUtils.getConnection();
			stmt = con
					.prepareStatement("update result_details set answer_option = ? where question_id = ? and result_id = ?");
			stmt.setString(1, rsultDetail.getAnswerOption());
			stmt.setInt(2, rsultDetail.getQuestionId());
			stmt.setInt(3, rsultDetail.getResultId());

			stmt.executeUpdate();

			/*
			 * stmt.executeUpdate("update result_details set answer_option = '"
			 * + rsultDetail.getAnswerOption() + "' where question_id = " +
			 * rsultDetail.getQuestionId() + " and result_id = " +
			 * rsultDetail.getResultId());
			 */
		} catch (SQLException e) {
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

	}

	@Override
	public void updateResult(int result_id, int result, double percentile,
			Timestamp time) {
		Connection con = null;
		Statement stmt = null;

		try {
			con = DBUtils.getConnection();
			stmt = con.createStatement();
			int rows = stmt.executeUpdate("update results set percentile = "
					+ percentile + ", result = " + result
					+ ", submitted_on = '" + time + "' where result_id = "
					+ result_id);
			System.out.println("result updated " + rows);
		} catch (SQLException e) {
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

	}

	@Override
	public Result getUserResultHistory(String dasId) {
		Connection con = null;
		PreparedStatement stmt = null;
		Result result = null;
		try {
			con = DBUtils.getConnection();

			stmt = con
					.prepareStatement(DBUtils.getQuery("user.result.history"));
			stmt.setString(1, dasId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				result = new Result();
				result.setResultId(rs.getInt("result_id"));
				result.setExamId(rs.getInt("exam_id"));
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

		return result;

	}

	@Override
	public List<ManagerReport> getManagerReport(String technology,
			Integer level, String das_id) {
		Connection con = null;
		PreparedStatement stmt = null;
		List<ManagerReport> resultList = null;
		try {
			con = DBUtils.getConnection();
			if (-1 != level && !"".equalsIgnoreCase(das_id)) {
				stmt = con.prepareStatement(DBUtils
						.getQuery("manager.report.technology.level.dasid"));
				stmt.setInt(2, level);
				stmt.setString(3, das_id);
			} else if (-1 != level && "".equalsIgnoreCase(das_id)) {
				stmt = con.prepareStatement(DBUtils
						.getQuery("manager.report.technology.level"));
				stmt.setInt(2, level);
			} else if (-1 == level && !"".equalsIgnoreCase(das_id)) {
				stmt = con.prepareStatement(DBUtils
						.getQuery("manager.report.technology.dasid"));
				stmt.setString(2, das_id);
			} else if (-1 == level && "".equalsIgnoreCase(das_id)) {
				stmt = con.prepareStatement(DBUtils
						.getQuery("manager.report.technology"));
			}
			stmt.setString(1, technology);
			ResultSet rs = stmt.executeQuery();
			resultList = new ArrayList<ManagerReport>(rs.getFetchSize());
			while (rs.next()) {
				ManagerReport res = new ManagerReport();
				res.setDas_id(rs.getString("dasID"));
				res.setName(rs.getString("name"));
				res.setLast_level(rs.getInt("last_level"));
				res.setSubmitted_date(rs.getDate("submitted"));
				resultList.add(res);
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
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException logOrIgnore) {
				}
			if (con != null)
				try {
					con.close();
				} catch (SQLException logOrIgnore) {
				}
		}
		return resultList;
	}

}
