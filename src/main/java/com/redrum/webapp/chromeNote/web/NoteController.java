package com.redrum.webapp.chromeNote.web;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.redrum.webapp.chromeNote.entity.Note;
import com.redrum.webapp.chromeNote.service.NoteService;
import com.redrum.webapp.entity.TestEntity;
import com.redrum.webapp.service.TestService;

@Controller
@RequestMapping(value = "/note")
public class NoteController {

	@Autowired
	private NoteService noteService;

	@RequestMapping(value = "/addNote")
	@ResponseBody
	public Integer addNote(HttpServletRequest request, @RequestParam Long timestamp){
		Note note = new Note();
		try {
			BeanUtils.populate(note, request.getParameterMap());
			if(null == note.getCreated())
				note.setCreated(new Timestamp(timestamp));
			note.setUpdated(new Timestamp(timestamp));
			return noteService.addNote(note);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping(value = "/saveNote")
	@ResponseBody
	public void saveNote(HttpServletRequest request, @RequestParam Long timestamp){
		Note note = new Note();
		try {
			BeanUtils.populate(note, request.getParameterMap());
			note.setUpdated(new Timestamp(timestamp));
			noteService.saveNote(note);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@RequestMapping(value = "/queryNotesByUrl")
	@ResponseBody
	public List<Note> queryNotesByUrl(@RequestParam String url, @RequestParam String userId) throws SQLException{
		return noteService.queryNotesByUrl(url, userId);
	}

}
