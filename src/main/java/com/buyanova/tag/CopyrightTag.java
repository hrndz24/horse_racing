package com.buyanova.tag;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Calendar;

public class CopyrightTag extends TagSupport {

    private Logger logger = LogManager.getLogger(CopyrightTag.class);

    @Override
    public int doStartTag() {
        String author = "©<b>Natalie&Co</b>";
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        try {
            JspWriter out = pageContext.getOut();
            String copyrightText = author + ", " + year;
            out.write(copyrightText);
        } catch (IOException e) {
            logger.warn("Problems with copyright tag writer", e);
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() {
        return EVAL_PAGE;
    }
}
