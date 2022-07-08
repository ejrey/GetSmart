# GetSmart

## Launching Application
- Begin by opening the entire project in Intellij
- Server
  - Open server/Server.java, click the little green arrow by `public static void main(String[] args)` and run it.
- Client
  - Open client/Client.java, click the little green arrow by `public static void main(String[] args)` and run it.
- What about testing multiple clients?
  - If you've previously ran the client `main()` method Intellij will populate your IDE with a `run config`. You should 
  to be able to view these at the top right of your IDE, a little drop-down box to the left of a green run button.
  - Click the drop-down menu, and then `Edit Configurations`
  - Select the `client` `run config` and click `Modify options`, enable `Allow multiple instances`
  - You can now run multiple instances of the client