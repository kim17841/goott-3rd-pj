package com.goott.pj3.board.qna.controller;

import com.goott.pj3.board.free.dto.Criteria;
import com.goott.pj3.board.free.dto.PagingDTO;
import com.goott.pj3.board.qna.dto.QnaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import com.goott.pj3.board.qna.service.QnaService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("qna/**")
public class QnaController {

	@Autowired
	QnaService qnaService;

	@RequestMapping("main")
	public ModelAndView main(ModelAndView mv){
		mv.addObject("data_n", qnaService.list_n());
		mv.addObject("data_u", qnaService.list_u());
		mv.addObject("data_r", qnaService.list_r());
		mv.addObject("data_e", qnaService.list_e());
		mv.setViewName("board/qna/qna_main");
		return mv;
	}

	@RequestMapping("create")
	public String Enroll() {
		return "board/qna/qna_enroll";
	}

	@RequestMapping(value = "enroll", method = RequestMethod.POST)
	public String enroll(QnaDTO qnaDTO, HttpSession session) {
		String user_id = (String) session.getAttribute("user_id");
		qnaDTO.setUser_id(user_id);
		qnaService.enroll(qnaDTO);
		return "redirect:/qna/list";
	}

	@RequestMapping(value = {"list_n","list_u","list_r","list_e"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public ModelAndView list(ModelAndView mv, Criteria cri, HttpServletRequest request) {
		String requestUrl = request.getRequestURL().toString();
		PagingDTO paging = new PagingDTO();
		if(requestUrl.contains("list_n")){
			cri.setCategory("n");
			paging.setCri(cri); // page / perpagenum 설정
			paging.setTotalCount(qnaService.totalCount(cri)); // 총게시글 갯수 불러오는 것
			mv.addObject("paging", paging);
			mv.addObject("list", qnaService.list(cri));
			mv.setViewName("board/qna/qna_list");
		}
		else if(requestUrl.contains("list_u")){
			cri.setCategory("u");
			paging.setCri(cri); // page / perpagenum 설정
			paging.setTotalCount(qnaService.totalCount(cri)); // 총게시글 갯수 불러오는 것
			mv.addObject("paging", paging);
			mv.addObject("list", qnaService.list(cri));
			mv.setViewName("board/qna/qna_list");
		}
		else if(requestUrl.contains("list_r")){
			cri.setCategory("r");
			paging.setCri(cri); // page / perpagenum 설정
			paging.setTotalCount(qnaService.totalCount(cri)); // 총게시글 갯수 불러오는 것
			mv.addObject("paging", paging);
			mv.addObject("list", qnaService.list(cri));
			mv.setViewName("board/qna/qna_list");
		}
		else{
			cri.setCategory("e");
			paging.setCri(cri); // page / perpagenum 설정
			paging.setTotalCount(qnaService.totalCount(cri)); // 총게시글 갯수 불러오는 것
			mv.addObject("paging", paging);
			mv.addObject("list", qnaService.list(cri));
			mv.setViewName("board/qna/qna_list");
		}
		return mv;
	}

	@RequestMapping("detail/{qna_idx}")
	public ModelAndView freeDetail(ModelAndView mv, @PathVariable int qna_idx) {
		mv.addObject("data", this.qnaService.detail(qna_idx));
		mv.setViewName("board/qna/qna_detail");
		return mv;
	}

	@RequestMapping(value = "modify", method = RequestMethod.POST, produces="application/text; charset=UTF-8;")
	@ResponseBody
	public String modify(QnaDTO qnaDTO) {
		qnaService.modify(qnaDTO);
		return "수정완료";
	}
	//
	@RequestMapping("delete")
	public String delete(int qna_idx) {
		qnaService.delete(qna_idx);
		return "redirect:/qna/list";
	}
}