import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Text Parser class is for handling all kinds of text parsing that needs to be applied on a text blurb
 * <p>
 * Usage:
 * TextParser textParser = new TextParser(input);
 * String output = textParser.parse();
 */
public class TextParser {
    public static final String A_HREF_OPEN = "<a href=\"";
    public static final String A_HREF_CLOSE = "\">";
    public static final String A_CLOSE = "</a>";
    public static final String HTTP = "http://";
    private static final Pattern A_PATTERN = Pattern.compile("(>([^<>]+)(?<!<\\/a)<)");
    private static final Pattern HTTP_URL_PATTERN = Pattern.compile("((mailto\\:|((ht|f)tp(s?))\\://){1}\\S+)");
    private static final Pattern WWW_URL_PATTERN = Pattern.compile("\\b(www\\.[\\S]+)");

    private String text;
    private String parsedText;

    /**
     * Constructor with input text as a param
     */
    public TextParser(String text) {
        this.text = text;
    }

    /**
     * getter for the input text
     */
    public String getText() {
        return text;
    }

    /**
     * setter for input text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * getter for parsed text
     */
    public String getParsedText() {
        return parsedText;
    }

    /**
     * parse method which does all the work
     */
    public String parse() {
        if (text == null) {
            return null;
        }

        parsedText = parseHttpUrls(text);
        parsedText = parseWWWUrls(parsedText);
        // other parsing methods can go here

        return parsedText;
    }

    private String parseHttpUrls(String text) {
        Matcher m1 = A_PATTERN.matcher(text);
        if (m1.find()) {
            // check if already linkified
            return text;
        } else {
            StringBuffer sb = new StringBuffer();

            // do the linkification
            Matcher m = HTTP_URL_PATTERN.matcher(text);
            while (m.find()) {
                m.appendReplacement(sb, getHttpUrlReplacementString(m));
            }
            m.appendTail(sb);

            return sb.toString();
        }
    }

    private String parseWWWUrls(String text) {
        StringBuffer sb = new StringBuffer();

        Matcher m = WWW_URL_PATTERN.matcher(text);
        while (m.find()) {
            m.appendReplacement(sb, getWWWUrlReplacementString(m));
        }
        m.appendTail(sb);

        return sb.toString();
    }

    private String getHttpUrlReplacementString(Matcher m) {
        StringBuffer sb = new StringBuffer();
        sb.append(A_HREF_OPEN);
        sb.append(m.group());
        sb.append(A_HREF_CLOSE);
        sb.append(m.group());
        sb.append(A_CLOSE);
        return sb.toString();
    }

    private String getWWWUrlReplacementString(Matcher m) {
        StringBuffer sb = new StringBuffer();
        sb.append(A_HREF_OPEN);
        sb.append(HTTP);
        sb.append(m.group());
        sb.append(A_HREF_CLOSE);
        sb.append(HTTP);
        sb.append(m.group());
        sb.append(A_CLOSE);
        return sb.toString();
    }
}