// Parsing XML files board.xlm and cards.xml, is associated with Card.java, Area.java and its children, Role.java
//
// CSCI 345

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;


import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class ParseGamePiecesXML{


    // Note: Author of function is Dr. Moushumi Sharmin
    //
    // building a document from the XML file
    // returns a Document object after loading the book.xml file.
    public Document getDocFromFile(String filename)
            throws ParserConfigurationException{
        {


            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = null;

            try{
                doc = db.parse(filename);
            } catch (Exception ex){
                System.out.println("XML parse failure");
                ex.printStackTrace();
            }
            return doc;
        } // exception handling

    }

    // reads data from XML file and initializes Cards and their Roles
    public Card[] initCards(String cardFilename){

        Document cardsD = getDocFromFile(cardFilename);

        Element root = cardsD.getDocumentElement();

        NodeList cards = root.getElementsByTagName("card");

        Card[] cardArray = new Card[cards.getLength()];

        for (int i=0; i<cards.getLength();i++){

            //reads data from the nodes
            Node card = cards.item(i);
            String sceneName = card.getAttributes().getNamedItem("name").getNodeValue();
            int budget = Integer.parseInt(card.getAttributes().getNamedItem("budget").getNodeValue());

            //reads data

            NodeList children = card.getChildNodes();
            String sceneDescription = children.item(0).getTextContent;
            Role[] roles = new Role[children.getLength()-1];

            for (int j=1; j< children.getLength(); j++){

                Node sub = children.item(j);

                else if("part".equals(sub.getNodeName())){
                    String roleName = sub.getAttributes().getNamedItem("name").getNodeValue();
                    int rank = Integer.parseInt(sub.getAttributes().getNamedItem("level").getNodeValue());
                    String line = sub.getChildNodes().item(1).getNodeValue();
                    role[j-1] = new Role(rank, roleName, line);
                }

            } //for childnodes

            cardArray[i] = new Card(roles, sceneName, sceneDesciption, budget);

        }//for card nodes

        return cardArray;

    }// method


    // reads data from XML file and initializes all areas
    public Area[] initAreas(Document d){

        Element root = d.getDocumentElement();

        NodeList sets = root.getElementsByTagName("set");

        Area[] areaArray = new Area[sets.getLength()+2];

        for (int i=0; i<sets.getLength();i++){

            //reads data from the nodes
            Node set = sets.item(i);

            Area[i] = initSet(set);

        }//for set nodes

        NodeList trailer = root.getElementsByTagName("trailer");

        NodeList trailer = root.getElementsByTagName("trailer");

    }// method

    // reads data from XML file and initializes Trailer
    public Area initTrailer(Node newOffice){
        String[] actions = {};

    }// method


    // reads data from XML file and initializes CastingOffice
    public Area initCastingOffice(Node newOffice){
        String[] actions = {"Move", "Upgrade"};

    }// method


    // reads data from XML file and initializes a Set and its Roles
    public Area initSet(Node newSet){
        String[] actions = {"Move", "Take Role", "Rehearse", "Act"};
        Set setObj;

        //reads data

        NodeList children = newSet.getChildNodes();
        

    }// method

}//class