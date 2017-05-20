package net.atos.awl.tes.cert.service;

import java.sql.Timestamp;
import java.util.List;

import net.atos.awl.tes.cert.vo.LevelExam;
import net.atos.awl.tes.cert.vo.ManagerReport;
import net.atos.awl.tes.cert.vo.Result;
import net.atos.awl.tes.cert.vo.ResultDetails;

public interface ResultService {
	public List<Result> getAllResults(String dasId);

	public Result getResult(String dasId, String technology, Integer level);

	public Result getResult(int result_id);

	public int createResult(String dasId, String technology, String examId, double percentile,
			int result);

	public LevelExam getLevelExamResult(String technology, Integer level);

	public Integer getAttemptCount(String dasId, String technology, Integer level);

	public List<Result> getReviewResultList(String technology, Integer level);

	public void createResultDetails(List<ResultDetails> rsultDetailsList);

	public void updateResultDetails(List<ResultDetails> rsultDetailsList);

	public List<ResultDetails> getResultDetails(String resultId);

	public void updateResult(int result_id, int result, double percentile, Timestamp time);

	public Result getUserResultHistory(String dasId);

	public boolean searchQuestions(int resultId, List<Integer> qstionIdList);

	public void updateResultDetails(ResultDetails resultDetail);

	public void updateResultDetailsForReview(List<ResultDetails> rsultDetailsList);

	public List<ResultDetails> getResultDetailsByType(String resultId, int type);

	public Integer getUnReviewedDescQuestionCount(String resultId);

	public List<ManagerReport> getManagerReport(String technology,
			Integer level, String das_id);
}
