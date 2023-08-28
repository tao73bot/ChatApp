import java.util.HashMap;
import java.util.Map;

public class ReaderWriterServer implements Runnable{
    String username;
    NetworkConnection nc;
    HashMap<String, Information> clientList;

    public ReaderWriterServer(String user, NetworkConnection netConnection, HashMap<String, Information> cList) {
        username = user;
        nc = netConnection;
        clientList = cList;
    }

    @Override
    public void run() {
        while (true) {
            Object obj = nc.read();
            Data dataObj=(Data)obj;
            String actualMessage=dataObj.message;
            System.out.println(actualMessage);
            if (actualMessage.toLowerCase().contains("list")) {
                System.out.println("List asked.." + actualMessage);
                String words[] = actualMessage.split("\\$");

                System.out.println("Client List: \n" + clientList);
                Information info = clientList.get(words[0]);
                String msgToSend = new String("List of Clients...\n");
                for (Map.Entry<String, Information> entry : clientList.entrySet()) {
                    String key = entry.getKey();

                    msgToSend = new String(msgToSend + key + "\n");

                }
                Object object = msgToSend;
                System.out.println("sending.." + msgToSend);
                System.out.println("words0: " + words[0]);
                info.netConnection.write(msgToSend);

            }
            if (actualMessage.toLowerCase().contains("ip")){
                String words[] = actualMessage.split("\\$");

                System.out.println("Client List: \n" + clientList);
                Information info = clientList.get(words[0]);
                String msgToSend = new String("Your PORT: \n");
                msgToSend+=info.netConnection.getSocket().getLocalAddress().getHostAddress();
                Object object = msgToSend;
                System.out.println("sending.." + msgToSend);
                System.out.println("words0: " + words[0]);
                info.netConnection.write(msgToSend);
            }
            if (actualMessage.toLowerCase().contains("send")){
                String words[] = actualMessage.split("\\$");
                Information info = clientList.get(words[1]);
                String msgToSend = words[0]+" says: " + words[3];
                Object object = msgToSend;
                System.out.println("sending.." + msgToSend);
                System.out.println("words0: " + words[0]);
                info.netConnection.write(msgToSend);
            }
        }

    }
}
