package sylvanas.connector;

import sylvanas.util.http.Parameters;

/**
 * @Description:
 */
public class RawRequest {

    private Parameters parameters = null;

    private String rawData = null;

    private String method = null;

    private String query = null;

    private String protocol = null;

    public RawRequest(String rawData){
        this.rawData = rawData;
        parseRequestLine();
    }

    public void parseRequestLine(){

        String rawRequestLine = rawData.substring(0,rawData.indexOf("\r\n"));
        String[] result = rawRequestLine.split(" ");

        if (result.length!=3){
            //TODO
        }

        method = result[0];
        query = result[1];
        protocol = result[2];

        parameters = new Parameters();
        parameters.setQuery(query);
        parameters.parse();

    }




    public void parseHeaders(){

    }
}
