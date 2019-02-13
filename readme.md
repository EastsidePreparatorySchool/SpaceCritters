# Introduction to SpaceCritters

SpaceCritters is an agent-based simulation game. That�s quite the term, but it really only means that YOU write an agent � a Java component implementing an interface to behave like an �Alien� � that is then injected into a big simulation where it can frolic in a two-dimensional space with other aliens.

You alien lives on a grid, 5000x5000 squares. Also on the grid are some stars and planets � loosely based on our sun and the stars within a 25-lightyear-sphere around us. Aliens can move freely in this grid � although they must take care not to move into a star, stand in the way of a planet, or be attacked by other aliens. There are two kind of alien � simple and complex. The differ in the way they move, see below.

Aliens have two attributes controlled by the game � a level of energy, and a level of technology. Aliens can take action to gain energy, or to research more technology, among other actions. If an alien runs out of energy, it dies. But never fear, aliens can also spawn new instances of themselves if they have enough energy. Better save up!

The game has turns. In each turn, there are multiple phases, the most important of which are:

1) Moving
2) Actions

In the moving phase, each alien is asked whether it wants to move. It can look around using a kind of radar (the reach depends on its level of technology), and it can make a decision to move a number of spots in any direction. The game gathers all moves from all aliens, and updates their position. Simple aliens move in integer spots in x and y directions, complex aliens behave quite differently � they are in elliptical orbits around stars or planets, and a move for the is an acceleration in a direction, changing their orbit (or taking them out on a non-bound trajectory). Aliens who leave the playing field, die.

Next, the game asks each alien whether it wants to take an action. Aliens can look at how much energy and technology they have, and then decide to take one of a few actions such as gaining energy, researching technology, fighting other aliens on the same spot as them, trading with them (but also being prepared to defend themselves in a fight), landing on a planet, or taking off from a planet.

To make an alien, your class implements the Alien interface. Let�s take a look at it:

public interface Alien {

    void init(Context ctx, int id, int parent, String message);

This method is called right after the object is created. The game passes along a context � a handle to the game, to do things, a unique id for this alien, the id of the parent (if it was spawned by one), and an optional message from a parent. Aliens must store the context in a member variable, they will need it. Nothing else is required in this method. 

    void communicate();

In this method, the alien can use the context object to send messages to whoever is ready to receive them within a certain radius.

    void receive(String[] messages);

In this method, the alien receives all the messages for which it was in range. This includes messages from other alien species � if you don�t want to be overheard, better encrypt!

    Vector2 getMove();

Here, an alien decides whether to move, and where. A Vector2 is simply a tuple of x and y. For complex aliens, this vector is an acceleration vector, which changes their current velocity and thereby their orbit.

    Action getAction();

Aliens can return actions from this method, after carefull reviewing things that might influence their decision � such as energy, tech, who is around them, what have they heard etc.

    void processResults();

In this method, aliens are given a chance to think about what happens in this turn. Nothing is required.

}


To make things easier at the beginning, every method is allowed to simply have one line of code:

        throw new UnsupportedOperationException("Not supported yet."); 

In fact, the EmpyAlien is just that:

public class EmptyAlien implements Alien {

    @Override
    public void init(Context ctx, int id, int parent, String message) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void communicate() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void receive(String[] messages) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public Direction getMove() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public Action getAction() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void processResults() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}


SpaceCritters is implemented as a web server. In some future, we will run it at school and you will be able to upload your alien to play with 
other aliens, but we are not there yet. Meanwhile, you can go to https://github.com/EastsidePreparatorySchool/SpaceCritters and download the
 zip file using the green button on the right. Extract all, and import the inner zip file / project "zerg.zip" into NetBeans. Pick a name for your new alien, make sure its class-name
 ends in �Alien� Then Right-click the new Zerg project to rename it. Then right-click, refactor, rename the zerg package, then the class. The
 project contains a number of libraries which are in fact the whole game server. When you �run� your alien, it will start the server but not other
 than a number of messages, not much will happen. Open a browser (SpaceCritters was tested in Chrome, Safari, and Edge), enter �localhost:8080� as
 the address, and hit enter. The splash screen for the game should load, and after 5 seconds you should see the main lobby. Click on the link 
for �main� to attach to that game. You should have a new tab with a grid, stars, and at least a voyager alien ready to pursue its orbit around the sun.
 Click start to start the game. Your alien should be in the list on the right, pause the game and click its checkbox, then start the game again to 
bring it alive. It will be a small box, like the other aliens. 

That�s all for now � enjoy playing. Let me know of errors in this document, or any problems with the game.
