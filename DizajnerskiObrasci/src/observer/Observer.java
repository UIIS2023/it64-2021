package observer;

public interface Observer {
	void update(int numberOfShapesOnScreen, int numberOfSelectedShapes, boolean isFirst, boolean isLast, int numberOfElementsInUndoList, int numberOfElementsInRedoList, int numberOfLogsForNext);
}
