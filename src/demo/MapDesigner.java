/*
 * This is free and unencumbered software released into the public domain.
 *
 * Anyone is free to copy, modify, publish, use, compile, sell, or
 * distribute this software, either in source code form or as a compiled
 * binary, for any purpose, commercial or non-commercial, and by any
 * means.
 * 
 * In jurisdictions that recognize copyright laws, the author or authors
 * of this software dedicate any and all copyright interest in the
 * software to the public domain. We make this dedication for the benefit
 * of the public at large and to the detriment of our heirs and
 * successors. We intend this dedication to be an overt act of
 * relinquishment in perpetuity of all present and future rights to this
 * software under copyright law.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 * 
 * For more information, please refer to <http://unlicense.org/>
 */

package demo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import net.krcko.map.MapNode;
import net.krcko.map.Path;
import net.krcko.math.Point2D;

/**
 * Graphical representation of the Map. Allows map editing.
 *
 * @author Aleksandar Ružičić
 */
public class MapDesigner extends JPanel implements MouseListener, MouseMotionListener, MapListener {

    public static final int PLAYER = -1;
    public static final int TARGET = -2;

    public int TILE_WIDTH = 30;
    public int TILE_HEIGHT = 30;

    public Color BACKGROUND = new Color(0xeeeeee);
    public Color GRASS = new Color(0xffffff);
    public Color SAND = new Color(0xe5e5e5);
    public Color MUD = new Color(0xb7b7b7);
    public Color WALL = new Color(0x4c4c4c);
    public Color GRID = new Color(0xcccccc);
    public Color TOOLTIP_TEXT = new Color(0x716f64);
    public Color TOOLTIP_BACK = new Color(0xcce7f3fc, true);

    private int brush = Map.GRASS;

    private boolean paintingAllowed = true;

    private Player player = new Player(-1, -1);
    private Point2D.Int target = new Point2D.Int(-1, -1);

    private Path<Point2D.Int> path = null;

    private Image PLAYER_IMAGE;
    private Image TARGET_IMAGE;
    private Image STEP_IMAGE;

    private HashMap<Point2D.Int, MapNode<Point2D.Int>> traversed = new HashMap<>();
    private MapNode<Point2D.Int> tooltipNode = null;
    private Point2D.Int mousePosition = null;

    /**
     * The Map instance
     */
    private Map map = new Map();

    public MapDesigner(final int width, final int height) {

        setDoubleBuffered(true);

        PLAYER_IMAGE = new ImageIcon(getClass().getResource("/demo/resources/player.png")).getImage();
        TARGET_IMAGE = new ImageIcon(getClass().getResource("/demo/resources/target.png")).getImage();
        STEP_IMAGE = new ImageIcon(getClass().getResource("/demo/resources/step.png")).getImage();
        
        setup(width, height);
    }
    
    private void setup(final int width, final int height)
    {
        resizeMap(width, height);

        addMouseListener(this);
        addMouseMotionListener(this);
        map.addMapListener(this);
    }

    public void resizeMap(final int width, final int height) {

        map.resize(width, height);
        
        Dimension size = new Dimension(width * TILE_WIDTH + 2, height * TILE_HEIGHT + 2);
        
        setSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
        setMinimumSize(size);

        repaint();
    }

    public int getBrush() {
        return brush;
    }

    public void setBrush(final int brush) {
        this.brush = brush;
    }

    public boolean isPaintingAllowed() {
        return paintingAllowed;
    }

    public void setPaintingAllowed(final boolean paintingAllowed) {
        this.paintingAllowed = paintingAllowed;
    }

    public void findPath(final int heuristic) throws Exception {

        if (!map.isValidLocation(player.getLocation())) {
            throw new Exception("Player could not be found on the map.");
        }

        if (!map.isValidLocation(target)) {
            throw new Exception("Target could not be found on the map.");
        }

        traversed.clear();
        path = map.findPath(heuristic, player, player.getLocation(), target);

        if (path == null) {
            throw new Exception("Could not find a path.");
        }
        
        paintingAllowed = false;
        repaint();
    }

    public void reset() {
        path = null;
        paintingAllowed = true;
        traversed.clear();
        repaint();
    }

    public void setDiagonalMovement(final boolean allowed) {
        map.setCanMoveDiagonaly(allowed);
    }

    public void clear() {
        map.resize(map.getWidthInTiles(), map.getHeightInTiles());
        reset();
    }

    @Override
    protected void paintComponent(final Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setBackground(BACKGROUND);
        g2d.clearRect(0, 0, getWidth(), getHeight());
        
        int width = map.getWidthInTiles() * (TILE_WIDTH + 1);
        int height = map.getHeightInTiles() * (TILE_HEIGHT + 1);

        g2d.setBackground(GRASS);
        g2d.setColor(GRID);

        g2d.clearRect(0, 0, width, height);
        g2d.drawRect(0, 0, width, height);

        for (int x = 1; x < map.getWidthInTiles(); x++) {
            g2d.drawLine(x * (TILE_WIDTH + 1), 0, x * (TILE_WIDTH + 1), height - 1);
        }

        for (int y = 1; y < map.getHeightInTiles(); y++) {
            g2d.drawLine(0, y * (TILE_HEIGHT + 1), width - 1, y * (TILE_HEIGHT + 1));
        }

        for (int x = 0; x < map.getWidthInTiles(); x++) {
            for (int y = 0; y < map.getHeightInTiles(); y++) {

                int value = map.getTile(x, y);

                if (value == Map.MUD) {
                    g2d.setColor(MUD);
                } else if (value == Map.SAND) {
                    g2d.setColor(SAND);
                } else if (value == Map.WALL) {
                    g2d.setColor(WALL);
                } else {
                    continue;
                }

                g2d.fillRect(x * (TILE_WIDTH + 1) + 1, y * (TILE_HEIGHT + 1) + 1, TILE_WIDTH, TILE_HEIGHT);
            }
        }

        if (map.isValidLocation(target)) {
            g2d.drawImage(TARGET_IMAGE, target.x * (TILE_WIDTH + 1) + 1, target.y * (TILE_HEIGHT + 1) + 1, null);
        }

        Point2D.Int location = player.getLocation();
        if (map.isValidLocation(location)) {
            g2d.drawImage(PLAYER_IMAGE, location.x * (TILE_WIDTH + 1) + 1, location.y * (TILE_HEIGHT + 1) + 1, null);
        }

        if (path != null) {
            for (int i = 0; i < path.getLength() - 1; i++) {
                g2d.drawImage(STEP_IMAGE, path.getStep(i).x * (TILE_WIDTH + 1) + 1, path.getStep(i).y * (TILE_HEIGHT + 1) + 1, null);
            }
        }
        
        g2d.setColor(Color.BLACK);
        for (MapNode<Point2D.Int> node: traversed.values()) {
            g2d.drawRect(node.getCoordinate().x * (TILE_WIDTH + 1), node.getCoordinate().y * (TILE_HEIGHT + 1), TILE_WIDTH + 1, TILE_HEIGHT + 1);
        }

        if (tooltipNode != null) {
            int offset = tooltipNode.getParent() == null ? 0 : 1;
            String[] info = new String[4 + offset];
            
            info[0] = "Coordinates: (" + tooltipNode.getCoordinate().x + ", " + tooltipNode.getCoordinate().y + ")";

            if (offset == 1) {
                info[1] = "Parent: (" + tooltipNode.getParent().getCoordinate().x + ", " + tooltipNode.getParent().getCoordinate().y + ")";
            }
            info[1 + offset] = "Depth: " + tooltipNode.getDepth();
            info[2 + offset] = "Cost: " + tooltipNode.getCost();
            info[3 + offset] = "Heuristic: " + tooltipNode.getHeuristic();

            int textWidth = 0, lineHeight = 0;
            
            for (int i = 0; i < info.length; i++) {
                Rectangle2D rect = g2d.getFontMetrics().getStringBounds(info[i], g);
                textWidth = Math.max(textWidth, (int) rect.getWidth());
                lineHeight = Math.max(lineHeight, (int) rect.getHeight());
            }
            
            final int lineSpacing = 3;
            int textHeight = lineHeight * info.length + lineSpacing * (info.length - 1);

            int x = mousePosition.x + 20;
            int y = mousePosition.y + 20;

            if (x + textWidth > width) {
                x = mousePosition.x - 5 - textWidth;
            }

            if (y + textHeight > height) {
                y = mousePosition.y - 5 - textHeight;
            }

            
            g2d.setColor(TOOLTIP_BACK);
            g2d.fillRoundRect(x, y, textWidth + 8, textHeight + 8, 8, 8);
            g2d.setColor(TOOLTIP_TEXT);
            g2d.drawRoundRect(x, y, textWidth + 8, textHeight + 8, 8, 8);
            for (int i = 0; i < info.length; i++) {
                g2d.drawString(info[i], x + 4, y + 14 + (lineHeight + lineSpacing) * i);
            }
        }
    }

    @Override
    public void mouseClicked(final MouseEvent e) {
        mouseDragged(e);
    }

    @Override
    public void mousePressed(final MouseEvent e) {
    }

    @Override
    public void mouseReleased(final MouseEvent e) {
    }

    @Override
    public void mouseEntered(final MouseEvent e) {
    }

    @Override
    public void mouseExited(final MouseEvent e) {
        if (tooltipNode != null) {
            tooltipNode = null;
            repaint();
        }
    }

    @Override
    public void mouseDragged(final MouseEvent e) {

        if (!paintingAllowed) {
            return;
        }

        int x = e.getX() / (TILE_WIDTH + 1);
        int y = e.getY() / (TILE_HEIGHT + 1);
        
        Point2D.Int location = new Point2D.Int(x, y);

        if (!map.isValidLocation(location)) {
            return;
        }

        if (brush == PLAYER) {
             player.setLocation(location);
        } else if (brush == TARGET) {
            target.setLocation(location);
        } else {
            try {
                map.setTile(x, y, brush);
            } catch (Exception ex) {
                return;
            }
        }

        repaint();
    }

    @Override
    public void mouseMoved(final MouseEvent e) {
        if (!paintingAllowed && !traversed.isEmpty()) {

            int x = e.getX() / (TILE_WIDTH + 1);
            int y = e.getY() / (TILE_HEIGHT + 1);

            Point2D.Int location = new Point2D.Int(x, y);

            if (traversed.containsKey(location)) {
                tooltipNode = traversed.get(location);
                mousePosition = new Point2D.Int(e.getX(), e.getY());
                repaint();
            } else if (tooltipNode != null) {
                tooltipNode = null;
                repaint();
            }
        }
    }

    @Override
    public void onNodeTraversed(final MapNode<Point2D.Int> node) {
        traversed.put(node.getCoordinate(), node);
        repaint();
    }
}
