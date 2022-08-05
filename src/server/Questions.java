package server;

public class Questions {
    public Question[] qs = new Question[] {
            //Networking, 100 to 500
            new Question(0,0,"A network router needs to implement",new String[]{"TCP, UDP, VPN","Everything in the OSN model","Just the network layer","the physical layer, the data link layer, the network layer"},"the physical layer, the data link layer, the network layer"),
            new Question(0,0,"Which of the TCP/IP layers handles the dividing of the incoming PDUs into frames?",new String[]{"(the transport layer","the networking layer","every layer","The data link layer."},"The data link layer."),
            new Question(0,0,"The preamble field in the header of Ethernet's MAC sublayer is used for",new String[]{"contemporization","simultaneity","three-way handshake","Synchronization"},"Synchronization"),
            new Question(0,0,"While a WiFi station is counting down its random backoff time, it suddenly hears a frame on the medium. What does the station do?",new String[]{"Disconnect","Continue counting","None of the above","Pause counting."},"Pause counting."),
            new Question(0,0,"When demultiplexing from the transport layer to the application layer, the transport layer looks at the",new String[]{"'protocol' field in the IP header","IP address of the destination"," None of the above","'port number' in the header"},"'port number' in the header"),
            //Sports, 100 to 500
            new Question(0,0,"What is the name of the hockey team that resides in Vancouver?",new String[]{"Giants","Lions","Whitecaps","Canucks"},"Canucks"),
            new Question(0,0,"When was the year the Toronto Raptors won their first NBA Championship?",new String[]{"2018","2017","2010","2019"},"2019"),
            new Question(0,0,"How many super bowl rings does Tom Brady have?",new String[]{"5","6","8","7"},"7"),
            new Question(0,0,"What’s the diameter of a basketball hoop in inches?",new String[]{"19 inches","17 inches","16 inches","18 inches"},"18 inches"),
            new Question(0,0,"How many sports were included in the 2008 Summer Olympics?",new String[]{"26","27","29","28"},"28"),
            //Canada, 100 to 500
            new Question(0,0,"What is the capital of Canada?",new String[]{"Ottawa","Toronto","Montreal","Winnipeg"},"Ottawa"),
            new Question(0,0,"Which was the last province to join Canada?",new String[]{"Yukon","Oregon","Quebec","Newfoundland"},"Newfoundland"),
            new Question(0,0,"Who served as the first Prime Minister of Canada?",new String[]{"Harry Styles","Ronald McDonald","Celine Dion","John A. Macdonald"},"John A. Macdonald"),
            new Question(0,0,"Who chose the name 'British Columbia'?",new String[]{"Governor James Douglas","Prime Minister Pierre Trudeau","Lieutenant-Governor Richard Moody","Queen Victoria"},"Queen Victoria"),
            new Question(0,0,"What is the most popular kids sport in Canada?",new String[]{"Soccer","Ice Hockey","Dance","Swimming"},"Swimming"),
            //Music 100 to 500
            new Question(0,0,"Before Miley Cyrus recorded 'Wrecking Ball,' it was offered to which singer?",new String[]{"Drake","Kanye West","Beyoncé","BTS"},"Beyoncé"),
            new Question(0,0,"Where was Tupac Shakur born?",new String[]{"East Harlem","China","Alabama","Toronto"},"East Harlem"),
            new Question(0,0,"Who won the top R&B Male Artist at the Billboard Music Awards 2018?",new String[]{"Bruno Mars","Drake","BTS", "Ariana Grande"},"Bruno Mars"),
            new Question(0,0,"In which country did Kanye West live when he was ten years old?",new String[]{"China","South Africa","France", "Brazil"},"China"),
            new Question(0,0,"Vanessa Carlton became a one-hit wonder for which 2001 song?",new String[]{"A Thousand Miles","Shake It Off","One Dance","Gangnam Style"},"A Thousand Miles"),
            //Geography, 100 to 500
            new Question(0,0,"Which country has the largest population in the world?",new String[]{"China","USA","Indonesia","India"},"China"),
            new Question(0,0,"The United States bought Alaska from which country?",new String[]{"Canada","China","Japan","Russia"},"Russia"),
            new Question(0,0,"What country has the most natural lakes?",new String[]{"Canada","Russia","England","USA"},"Canada"),
            new Question(0,0,"What is the capital of Mexico?",new String[]{"Mexico City","Mexico","Tijuana","Miami"},"Mexico City"),
            new Question(0,0,"Which country does NOT share a border with Egypt?",new String[]{"Chad","Libya","Israel","Saudi Arabia"},"Chad"),
            //animal Trivia 100 to 500

            new Question(0,0,"What is a group of lions called?",new String[]{"A pride","a groupie","a hoard","a bundle"},"A pride"),
            new Question(0,0,"How many bones does a shark have?",new String[]{"Less than 20","21-100","101-300","More than 300"},"Less than 20"),
            new Question(0,0,"What is a female donkey called?",new String[]{"Jenny","Heifer","Doe","Mare"},"Jenny"),
            new Question(0,0,"How fast can a blue whale swim?",new String[]{"21-40 km/h","11-20 km/h","< 10 km/h","> 41 km/h"},"> 41 km/h"),
            new Question(0,0,"What colour is a hippo's sweat?",new String[]{"Pink","Grey","Blue","White"},"Pink"),
    };
    public Question[][] currentQuestions;//starting from bottom left (0,0), i is column, j is row

    public int rows;
    public int columns;

    Questions(int columns,int rows ){
        currentQuestions = new Question[columns][rows];
        //Initialize the 2D question board based on the static 1D "qs" list.
        for(int column=0; column<columns; column++){
            for(int row=0; row<rows; row++){
                var currentQ = qs[(rows)*column+row];
                currentQ.setRow(row);
                currentQ.setColumn(column);
                currentQuestions[column][row] = currentQ;

            }
        }
    }
    public Question tryGetQuestion(int column, int row, String userName){
        return currentQuestions[column][row].tryGetQuestion(userName);
    }
    public Question getQuestion(int column, int row, String threadId){
        //Bypasses locking; used to get the question for checking the right answer
        return currentQuestions[column][row];
    }

}
