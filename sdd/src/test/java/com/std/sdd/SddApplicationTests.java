package com.std.sdd;



import com.std.sdd.answer.Repository.AnswerRepository;
import com.std.sdd.answer.entity.Answer;
import com.std.sdd.question.entity.Question;
import com.std.sdd.question.repository.QuestionRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
class SddApplicationTests {

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private AnswerRepository answerRepository;
	@Test
	@DisplayName("데이터 삽입")
	void createDate() {
		Question q1 = new Question();
		q1.setSubject("첫번째 제목입니다.");
		q1.setContent("첫번쨰 제목에 대한 내용입니다.");
		q1.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q1);

		Question q2 = new Question();
		q2.setSubject("두번째 제목입니다.");
		q2.setContent("두번째 제목에 대한 내용입니다.");
		q2.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q2);
	}
	@Test
	@DisplayName("데이터 조회")
	void findAll(){
		List<Question> all = this.questionRepository.findAll();
		assertEquals(2, all.size());

		Question q = all.get(0);
		assertEquals("첫번째 제목입니다.", q.getSubject());
	}

	@Test
	@DisplayName("id값으로 데이터 조회")
	void findById(){
		Optional<Question> oq = this.questionRepository.findById(1);
		if (oq.isPresent()){
			Question q = oq.get();
			assertEquals("첫번째 제목입니다.", q.getSubject());
		}
	}
	@Test
	@DisplayName("subjec값으로 데이터 조회")
	void findBySubject(){
		Question q = this.questionRepository.findBySubject("첫번째 제목입니다.");
		assertEquals(1, q.getId());
	}
	@Test
	@DisplayName("제목과 내용으로 데이터 조회")
	void findBySubjectAndContent(){
		Question q = this.questionRepository.findBySubjectAndContent("첫번째 제목입니다", "첫번째 제목에 대한 내용입니다.");
		assertEquals(1, q.getId());
	}
	@Test
	@DisplayName("특정 문자열로 데이터 조회")
	void findBySubjectLike(){
		List<Question> qlist = this.questionRepository.findBySubjectLike("%제목%");
		Question q = qlist.get(0);
		assertEquals("첫번째 제목입니다.", q.getSubject());
	}
	@Test
	@DisplayName("데이터 수정하기")
	void modify(){
		Optional<Question> oq = this.questionRepository.findById(2);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		q.setSubject("수정된 두번째 제목입니다.");
		q.setContent("수정된 두번째 제목의 내용입니다.");
		this.questionRepository.save(q);
	}
	@Test
	@DisplayName("데이터 삭제하기")
	void remove(){
		assertEquals(2, this.questionRepository.count());
		Optional<Question> oq = this.questionRepository.findById(1);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		this.questionRepository.delete(q);
		assertEquals(1, this.questionRepository.count());
	}
	@Test
	@DisplayName("답변 데이터 생성 후 저장")
	void answerCreate(){
		Optional<Question> oq = this.questionRepository.findById(2);
		assertTrue(oq.isPresent());
		Question q = oq.get();

		Answer a = new Answer();
		a.setContent("첫번째 답변이 잘 생성되었습니다.");
		a.setQuestion(q);
		a.setCreateDate(LocalDateTime.now());
		this.answerRepository.save(a);
	}
	@Test
	@DisplayName("답변 조회하기")
	void answerList(){
		Optional<Answer> oa = this.answerRepository.findById(1);
		assertTrue(oa.isPresent());
		Answer a = oa.get();
		assertEquals(2, a.getQuestion().getId());
	}
	@Transactional
	@Test
	@DisplayName("답변에 연결된 질문 찾기 vs 질문에 달린 답변 찾기")
	void findAnswerList(){
		Optional<Question> oq = this.questionRepository.findById(2);
		assertTrue(oq.isPresent());
		Question q = oq.get();

		List<Answer> aList = q.getAnswerList();

		assertEquals(1, aList.size());
		assertEquals("첫번째 답변이 잘 생성되었습니다.", aList.get(0).getContent());
	}
}
