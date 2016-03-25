import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Test class for TextParser
 */
public class TextParserTest {

    @DataProvider
    public Object[][] getData()
    {
        return new Object[][] {
                {null, null},
                {"", ""},
                {"text no url", "text no url"},
                {"http://google.com", "<a href=\"http://google.com\">http://google.com</a>"},
                {"https://amazon.com/", "<a href=\"https://amazon.com/\">https://amazon.com/</a>"},
                {"https://amazon.com/deals/top-deals/", "<a href=\"https://amazon.com/deals/top-deals/\">https://amazon.com/deals/top-deals/</a>"},
                {"check out https://amazon.com/best-deals for the best deals", "check out <a href=\"https://amazon.com/best-deals\">https://amazon.com/best-deals</a> for the best deals"},
                {"contact me at mailto:me@mydomain.com for details", "contact me at <a href=\"mailto:me@mydomain.com\">mailto:me@mydomain.com</a> for details"},
                {"download here: ftp://mydomain.com", "download here: <a href=\"ftp://mydomain.com\">ftp://mydomain.com</a>"},
                {"<a href=\"http://google.com\">http://google.com</a>", "<a href=\"http://google.com\">http://google.com</a>"},
                {"你好", "你好"},
                {"你好 http://amazon.com", "你好 <a href=\"http://amazon.com\">http://amazon.com</a>"},
                {"www.amazon.com/", "<a href=\"http://www.amazon.com/\">http://www.amazon.com/</a>"}
        };
    }

    @Test
    public void testGetText() throws Exception {
        String input = "input string";
        TextParser textParser = new TextParser(input);

        Assert.assertEquals(textParser.getText(), input);
    }

    @Test
    public void testSetText() throws Exception {
        String input = "input string";
        TextParser textParser = new TextParser("");
        textParser.setText(input);

        Assert.assertEquals(textParser.getText(), input);
    }

    @Test(dataProvider="getData")
    public void testParse(String input, String expectedOutput) throws Exception {
        TextParser textParser = new TextParser(input);
        textParser.parse();

        Assert.assertEquals(textParser.getParsedText(), expectedOutput);
    }
}
