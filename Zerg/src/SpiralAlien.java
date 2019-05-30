/*
 * This work is licensed under a Creative Commons Attribution-NonCommercial 3.0 United States License.
 * For more information go to http://creativecommons.org/licenses/by-nc/3.0/us/
 */

import org.eastsideprep.spacecritters.alieninterfaces.*;
//import com.interactivemesh.jfx.importer.stl.StlMeshImporter;

/**
 *
 * @author gmein
 */
public class SpiralAlien implements Alien/*, AlienShapeFactory */ {

    Context ctx;
    boolean tooComplex = false;
    Vector2 currentDir = null;
    int runLength;
    int runCount;

    final boolean debug = true;

    public SpiralAlien() {
    }

    @Override
    public void init(Context game_ctx, int id, int parent, String message) {
        ctx = game_ctx;
//        ctx.debugOut("Initialized at "
//                + ctx.getPosition().toString()
//                + " E: " + Double.toString(ctx.getEnergy())
//                + " T: " + Double.toString(ctx.getTech()));
        currentDir = new Direction(1, 0);
        runLength = 10;
        runCount = 0;
    }

    @Override
    public Vector2 getMove() {
        if (runCount++ > runLength) {
            runLength += 20;
            runCount = 0;
            currentDir = currentDir.rotate(Math.PI / 2);
        }
        
        return currentDir.scaleToLength(1);
    }

    @Override
    public Action getAction() {

//        ctx.debugOut("Action requested," + ctx.getStateString());
        // catch any shenanigans
        if (ctx.getEnergy() > 100) {
            try {
                View view = ctx.getView((int) ctx.getTech());
                if (view.getAliensAtPos(ctx.getPosition()).size() > 1) {
//                    ctx.debugOut("Uh-oh.There is someone else here."
//                            + ctx.getStateString());
                }

                // do we have enough energy?
                if (ctx.getEnergy() < 10) {
                    // no, charge
//                    ctx.debugOut("Choosing to gain energy,"
//                            + ctx.getStateString());
                    return new Action(Action.ActionCode.Gain);
                }

                // do we have enough tech?
                if (ctx.getTech() < 30 && ctx.getEnergy() > ctx.getTech()) {
                    // no, research
//                    ctx.debugOut("Choosing to research"
//                            + ctx.getStateString());
                    return new Action(Action.ActionCode.Research);
                }

                // is there another alien on our position?
                if (view.getAliensAtPos(ctx.getPosition()).size() > 1
                        && ctx.getEnergy() > ctx.getFightingCost() + 2) {
//                    ctx.debugOut("EXTERMINATE!!!!!"
//                            + ctx.getStateString());

                    return new Action(Action.ActionCode.Fight, (int) ctx.getEnergy() - 2 - ctx.getFightingCost());
                }

                if (ctx.getEnergy() > (ctx.getSpawningCost() + 10)) {
                    // no other aliens here, have enough stuff, spawn!
//                    ctx.debugOut("AAs RULE SUPREME! SPAWNING!"
//                            + ctx.getStateString());

                    return new Action(Action.ActionCode.Spawn, 5);
                }
            } catch (Exception e) {
                // do something here to deal with errors
//                ctx.debugOut("EXPLAIN?????? " + e.toString());
            }
        }
//        ctx.debugOut("Gaining energy"
//                + ctx.getStateString());
        return new Action(Action.ActionCode.Gain);
    }

    @Override
    public void communicate() {
        if (ctx.getEnergy() > 100) {
            try {
                if (ctx.getGameTurn() % 20 == 0) {
//                    ctx.broadcastAndListen("I say: AAs rule supreme!!!!!", 1, true);
                }
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void receive(String[] messages) {
        for (String s : messages) {
            ctx.debugOut(s);
        }
    }

    @Override
    public void processResults() {
    }
    /*
    @Override
    public Shape3D getShape(int complexityLimit) {
        MeshView dalek;
        if (dalekMesh == null) {
            try {
                StlMeshImporter importer = new StlMeshImporter();
                importer.read(this.getClass().getResource("/Resources/DalekFull2.stl"));
                dalekMesh = importer.getImport();
                tooComplex = (dalekMesh.getFaceElementSize() > complexityLimit);
            } catch (Exception e) {
                dalekMesh = null;
            }
        }

        if (dalekMesh != null && !tooComplex) {
            dalek = new MeshView(dalekMesh);
            dalek.getTransforms().setAll(new Scale(0.01, 0.01, 0.01));
            dalek.getTransforms().add(new Translate(0, 20, 0));
            dalek.getTransforms().add(new Rotate(-90, new Point3D(1, 0, 0)));
            return dalek;
        }
        return null;
    }
     */
}
