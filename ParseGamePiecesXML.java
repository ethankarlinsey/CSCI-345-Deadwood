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
    private Document getDocFromFile(String filename)
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

                if("part".equals(sub.getNodeName())){
                    role[j-1] = getRole(sub);
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

        for (int i=0; i<sets.getLength(); i++){

            //reads data from the nodes
            Node set = sets.item(i);

            Area[i] = initSet(set);

        }//for set nodes

        Node trailer = root.getElementsByTagName("trailer").item(0);
        areaArray[sets.getLength()] = initTrailer(trailer);

        Node office = root.getElementsByTagName("office").item(0);
        areaArray[sets.getLength()+1] = initCastingOffice(office);

    }// method

    // reads data from XML file and initializes Trailer
    private Area initTrailer(Node newTrailer){
        String[] neighbors = getNeighbors(newTrailer);

        Trailer trailer = new Trailer(neighbors);

        return trailer;
    }// method


    // reads data from XML file and initializes CastingOffice
    private Area initCastingOffice(Node newOffice){
        String[] neighbors = getNeighbors(newOffice);

        CastingOffice office = new CastingOffice(neighbors);

        return office;
    }// method


    // reads data from XML file and initializes a Set and its Roles
    private Area initSet(Node newSet){
        String setName = newSet.getAttributes().getNamedItem("name").getNodeValue();
        String[] neighbors = getNeighbors(newOffice);
        Role[] roles;
        int takes = 0;

        NodeList children = newSet.getChildNodes();

        for(int i=0; i < children.getLength(); i++){
            Node temp = children.item(i);

            if("takes".equals(temp.getNodeName())){
                // get total number of takes
                takes = Integer.parseInt(temp.getFirstChild().getAttributes().getNamedItem("number").getNodeValue());
            } else if ("parts".equals(temp.getNodeName())){
                NodeList parts = temp.getChildNodes();
                roles = new Role[parts.getLength()];

                for(int j=0; j < parts.getLength(); j++){
                    Node temp_part = parts.item(j);
                    if("part".equals(temp_part.getNodeName())) {
                        roles[j] = getRole(temp_part);
                    }
                }
            }
        }

        Set set = new Set(setName, roles, takes, neighbors);

        return set;
    }// method

    private String[] getNeighbors(Node node){
        NodeList neighbors = node.getElementsByTagName("neighbors").getElementsByTagName("neighbor");
        String[] adjacents = new String[neighbors.getLength()];

        for (int i=0; i < neighbors.getLength(); i++){
            Node temp = neighbors.item(i);

            adjacents[i] = temp.getAttributes().getNamedItem("name").getNodeValue();
        }

        return adjacents;
    }

    private Role getRole(Node node){
        String roleName = node.getAttributes().getNamedItem("name").getNodeValue();
        int rank = Integer.parseInt(node.getAttributes().getNamedItem("level").getNodeValue());
        String line = node.getChildNodes().item(1).getNodeValue();
        Role role = new Role(rank, roleName, line);

        return role;
    }

}//class