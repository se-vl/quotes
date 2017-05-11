package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/QuoteServlet")
public class QuoteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String[] wordsOfWisdom = {
			"Multicore processors are just now [May 2006] becoming inexpensive enough for midrange desktop systems. Not coincidentally, many development teams are noticing more and more threading-related bug reports in their projects.",
			"In a recent post on the Netbeans developer site, one of the core maintainers observed that a single class had been patched over 14 times to fix threading-related problems.",
			"Dion Almaer, former editor of TheServerSide, recently blogged (after a painful debugging session that ultimately revealed a threading bug) that most Java programs are so rife with concurrency bugs that they work only 'by accident'.",
			"It is all too easy to create programs that compile and appear to work but are nevertheless broken.",
			"It would be nice to believe that concurrency is an 'optional' or 'advanced' language feature, but the reality is that nearly all Java applications are multithreaded and these frameworks do not insulate you from the need to properly coordinate access to application state.",
			"Whenever more than one thread accesses a given state variable, and one of them might write to it, they all must coordinate their access to it using synchronization. You should avoid the temptation to think that there are 'special' situations in which this rule does not apply.",
			"A program that omits needed synchronization might appear to work, passing its tests and performing well for years, but it is still broken and may fail at any moment.", };

	private List<String> allQuotes = new ArrayList<>(Arrays.asList(wordsOfWisdom));

	private Random randomNumberGenerator = new Random();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println("<html><head><title>Random quotes for everybody</title></head>");
		out.println("<body><h2>Random quote</h2><p>");

		int numberOfQuotes = allQuotes.size();
		int randomIndex;
		HttpSession session = request.getSession();
		Integer previousRandomIndex = (Integer) session.getAttribute("previousRandomIndex");
		if (previousRandomIndex == null) {
			randomIndex = randomNumberGenerator.nextInt(numberOfQuotes);
		} else {
			randomIndex = randomNumberGenerator.nextInt(numberOfQuotes - 1);
			if (randomIndex == previousRandomIndex) {
				randomIndex = numberOfQuotes - 1;
			}
		}
		session.setAttribute("previousRandomIndex", randomIndex);
		String randomQuote = allQuotes.get(randomIndex);

		out.println("\"" + escapeHtml(randomQuote) + "\" (" + (randomIndex + 1) + "/" + numberOfQuotes + ")");
		out.println("</p><h2>Add quote</h2>");
		out.println("<form method='post'>");
		out.println("<input type='text' name='newQuote' size='100' />");
		out.println("<input type='submit' />");
		out.println("</form></body></html>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String newQuote = request.getParameter("newQuote");
		allQuotes.add(newQuote);

		out.println("<html><head><title>Random quotes for everybody</title></head>");
		out.println("<body><h2>All quotes</h2><ol>");
		for (String quote : allQuotes) {
			out.println("<li>" + escapeHtml(quote) + "</li>");
		}
		out.println("</ol></body></html>");
	}

	/**
	 * Replaces ampersand, angle brackets and quotes with their HTML escapes.
	 */
	private static String escapeHtml(String raw) {
		int len = raw.length();
		StringBuilder escaped = new StringBuilder(len);
		for (int i = 0; i < len; ++i) {
			char x = raw.charAt(i);
			switch (x) {
			case '&':
				escaped.append("&amp;");
				break;
			case '<':
				escaped.append("&lt;");
				break;
			case '>':
				escaped.append("&gt;");
				break;
			case '"':
				escaped.append("&quot;");
				break;
			case 39:
				escaped.append("&#39;");
				break;
			default:
				escaped.append(x);
			}
		}
		return escaped.toString();
	}
}
