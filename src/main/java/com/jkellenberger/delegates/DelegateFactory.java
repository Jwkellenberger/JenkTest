package com.jkellenberger.delegates;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

public class DelegateFactory {
	private static DelegateFactory df;
	private Map<String, FrontControllerDelegate> delegateMap;
	private DelegateFactory() {
		delegateMap = new HashMap<String, FrontControllerDelegate>();
//		delegateMap.put("books", new BookDelegate());
//		delegateMap.put("authors", new AuthorDelegate());
		delegateMap.put("login", new LoginDelegate());
		delegateMap.put("submission", new SubmissionDelegate());
//		delegateMap.put("genres", new GenreDelegate());
//		delegateMap.put("purchases", new PurchaseDelegate());
		delegateMap.put("", new BasePageDelegate());
		delegateMap.put("addBook", (r1,r2) -> {
			if(r1.getAttribute("path")!=null)
				r2.sendError(HttpServletResponse.SC_BAD_REQUEST);
			else
				r1.getRequestDispatcher("/static/addbook.html").forward(r1, r2);
		});
		delegateMap.put("getBooks", (r1,r2) -> {
			if(r1.getAttribute("path")!=null)
				r2.sendError(HttpServletResponse.SC_BAD_REQUEST);
			else
				r1.getRequestDispatcher("/static/books.html").forward(r1, r2);
		});
		delegateMap.put("editBook", (r1,r2) -> {
			if(r1.getAttribute("path")!=null)
				r1.getRequestDispatcher("/static/editbook.html").forward(r1, r2);
			else
				r2.sendError(HttpServletResponse.SC_BAD_REQUEST);
		});
	}

	public static synchronized DelegateFactory getInstance() {
		if (df == null)
			df = new DelegateFactory();
		return df;
	}

	public FrontControllerDelegate getDelegate(String name) {
		FrontControllerDelegate fcd = delegateMap.get(name);
		if(fcd==null) {
			fcd = (req, resp) -> {
				resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			};
		}
		return fcd;
	}
}
