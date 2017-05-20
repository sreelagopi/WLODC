/**
 * 
 */
package net.atos.awl.tes.cert.service;

import java.sql.Timestamp;
import java.util.List;

import net.atos.awl.tes.cert.dao.ResultDao;
import net.atos.awl.tes.cert.dao.ResultDaoImpl;
import net.atos.awl.tes.cert.vo.LevelExam;
import net.atos.awl.tes.cert.vo.ManagerReport;
import net.atos.awl.tes.cert.vo.Result;
import net.atos.awl.tes.cert.vo.ResultDetails;

/**
 * @author Gauri Chiplunkar
 * 
 */
public class ResultDaoService implements ResultService {

	private final ResultDao resultDao;

	public ResultDaoService() {
		resultDao = new ResultDaoImpl();
	}

	@Override
	public List<Result> getAllResults(String dasId) {
		return resultDao.getAllResults(dasId);
	}

	@Override
	public Result getResult(String dasId, String technology, Integer level) {
		return resultDao.getResult(dasId, technology, level);
	}

	@Override
	public Result getResult(int resultId) {
		return resultDao.getResult(resultId);
	}

	@Override
	public int createResult(String dasId, String technology, String examId, double percentile,
			int result) {
		return resultDao.createResult(dasId, technology, examId, percentile, result);
	}

	@Override
	public LevelExam getLevelExamResult(String technology, Integer level) {
		return resultDao.getLevelExamResult(technology, level);
	}

	@Override
	public Integer getAttemptCount(String dasId, String technology, Integer level) {
		return resultDao.getAttemptCount(dasId, technology, level);
	}

	@Override
	public List<Result> getReviewResultList(String technology, Integer level) {
		return resultDao.getReviewResultList(technology, level);
	}

	@Override
	public void createResultDetails(List<ResultDetails> rsultDetailsList) {
		System.out.println("ResultDAO creaResultDatail List size " + rsultDetailsList.size());
		resultDao.createResultDetails(rsultDetailsList);
	}

	@Override
	public void updateResultDetails(List<ResultDetails> rsultDetailsList) {
		resultDao.updateResultDetails(rsultDetailsList);
	}

	@Override
	public void updateResultDetails(ResultDetails resultDetail) {
		resultDao.updateResultDetails(resultDetail);
	}

	@Override
	public List<ResultDetails> getResultDetails(String resultId) {
		return resultDao.getResultDetails(resultId);
	}

	@Override
	public void updateResult(int result_id, int result, double percentile, Timestamp time) {
		resultDao.updateResult(result_id, result, percentile, time);
	}

	@Override
	public Result getUserResultHistory(String dasId) {
		return resultDao.getUserResultHistory(dasId);
	}

	@Override
	public boolean searchQuestions(int resultId, List<Integer> qstionIdList) {
		return resultDao.searchQuestions(resultId, qstionIdList);
	}

	@Override
	public void updateResultDetailsForReview(List<ResultDetails> rsultDetailsList) {
		resultDao.updateResultDetailsForReview(rsultDetailsList);
	}

	@Override
	public List<ResultDetails> getResultDetailsByType(String resultId, int type) {
		return resultDao.getResultDetailsByType(resultId, type);
	}

	@Override
	public Integer getUnReviewedDescQuestionCount(String resultId){
		return resultDao.getUnReviewedDescQuestionCount(resultId);
	}

	@Override
	public List<ManagerReport> getManagerReport(String technology,
			Integer level, String das_id) {
		return resultDao.getManagerReport(technology, level, das_id);
	}

}
