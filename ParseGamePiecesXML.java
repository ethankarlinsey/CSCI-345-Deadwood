// Parsing XML files board.xlm and cards.xml, is associated with Card.java, Area.java and its children, Role.java
//
// CSCI 345

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;

public class ParseGamePiecesXML{


    // Note: Author of function is Dr. Moushumi Sharmin
    //
    // building a document from the XML file
    // returns a Document object after loading the book.xml file.
    private Document getDocFromFile(String filename){

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = null;
            try {
                db = dbf.newDocumentBuilder();
            } catch (Exception ex) {
                System.out.println("Create document builder failure");
                ex.printStackTrace();
            }
            Document doc = null;

            try{
                doc = db.parse(filename);
            } catch (Exception ex){
                System.out.println("XML parse failure");
                ex.printStackTrace();
            }
            return doc;
            // exception handling

    }

    // reads data from XML file and initializes Cards and their Roles
    public ArrayList<Card> initCards(String cardFilename){

        Document cardsD = getDocFromFile(cardFilename);

        Element root = cardsD.getDocumentElement();

        NodeList cards = root.getElementsByTagName("card");

        ArrayList<Card> cardArray = new ArrayList<>();

        for (int i=0; i<cards.getLength();i++){

            //reads data from the nodes
            Node card = cards.item(i);
            String sceneName = card.getAttributes().getNamedItem("name").getNodeValue();
            int budget = Integer.parseInt(card.getAttributes().getNamedItem("budget").getNodeValue());

            //reads data

            NodeList children = card.getChildNodes();
            String sceneDescription = children.item(0).getTextContent();
            ArrayList<Role> roles = new ArrayList<>();

            for (int j=1; j< children.getLength(); j++){

                Node sub = children.item(j);

                if("part".equals(sub.getNodeName())){
                    roles.add(getRole(sub));
                }

            } //for childnodes
            cardArray.add(new Card(roles, sceneName, sceneDescription, budget));

        }//for card nodes

        return cardArray;

    }// method


    // reads data from XML file and initializes all areas
    public ArrayList<Area> initAreas(String boardFileName){

        Document areasD = getDocFromFile(boardFileName);

        Element root = areasD.getDocumentElement();

        NodeList sets = root.getElementsByTagName("set");

        ArrayList<Area> areaArray = new ArrayList<>();

        for (int i=0; i<sets.getLength(); i++){

            //reads data from the nodes
            Node set = sets.item(i);

            areaArray.add(initSet(set));

        }//for set nodes

        Node trailer = root.getElementsByTagName("trailer").item(0);
        areaArray.add(initTrailer(trailer));

        Node office = root.getElementsByTagName("office").item(0);
        areaArray.add(initCastingOffice(office));

        return areaArray;

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
        String[] neighbors = getNeighbors(newSet);
        ArrayList<Role> roles = new ArrayList<Role>();
        int takes = 0;

        NodeList children = newSet.getChildNodes();

        for(int i=0; i < children.getLength(); i++){
            Node temp = children.item(i);

            if("takes".equals(temp.getNodeName())){
                // get total number of takes
                Node firstChild = temp.getFirstChild().getNextSibling();
                NamedNodeMap attributes = firstChild.getAttributes();
                Node firstTake = attributes.getNamedItem("number");
                takes = Integer.parseInt(firstTake.getNodeValue());
            } else if ("parts".equals(temp.getNodeName())){
                NodeList parts = temp.getChildNodes();

                for(int j=0; j < parts.getLength(); j++){
                    Node temp_part = parts.item(j);
                    if("part".equals(temp_part.getNodeName())) {
                        roles.add(getRole(temp_part));
                    }
                }
            }
        }

        Set set = new Set(setName, roles, takes, neighbors);

        return set;
    }// method

    private String[] getNeighbors(Node node){
        NodeList neighbors = node.getFirstChild().getNextSibling().getChildNodes();
        ArrayList<String> adjacents = new ArrayList<>();

        for (int i=0; i < neighbors.getLength(); i++){
            Node temp = neighbors.item(i);
            if(temp.getNodeName().equals("neighbor")) {
                if(temp.getAttributes().getNamedItem("name").getNodeValue().equals("office")){
                    adjacents.add("Casting Office");
                } else {
                    adjacents.add(temp.getAttributes().getNamedItem("name").getNodeValue());
                }
            }
        }

        return adjacents.toArray(new String[0]);
    }

    private Role getRole(Node node){
        String roleName = node.getAttributes().getNamedItem("name").getNodeValue();
        int rank = Integer.parseInt(node.getAttributes().getNamedItem("level").getNodeValue());

        String line = "";
        NodeList childNodes = node.getChildNodes();
        for (int i=0; i < childNodes.getLength(); i++) {
            Node temp = childNodes.item(i);
            if (temp.getNodeName().equals("line")) {
                line = temp.getTextContent();
            }
        }
        Role role = new Role(rank, roleName, line);

        return role;
    }

}//class