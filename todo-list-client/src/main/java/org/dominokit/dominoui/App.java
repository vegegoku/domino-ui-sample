package org.dominokit.dominoui;

import com.google.gwt.core.client.EntryPoint;
import org.dominokit.domino.ui.button.Button;
import org.dominokit.domino.ui.button.IconButton;
import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.forms.TextArea;
import org.dominokit.domino.ui.forms.TextBox;
import org.dominokit.domino.ui.icons.Icons;
import org.dominokit.domino.ui.layout.Layout;
import org.dominokit.domino.ui.lists.ListGroup;
import org.dominokit.domino.ui.lists.ListItem;
import org.dominokit.domino.ui.popover.Tooltip;
import org.dominokit.domino.ui.style.StyleType;
import org.dominokit.domino.ui.themes.Theme;

public class App implements EntryPoint {

    public void onModuleLoad() {
        Layout layout = Layout.create("TODO-List")
                .removeLeftPanel().show(Theme.BLUE);

        TextBox title = TextBox.create("Title").floating();
        TextArea description = TextArea.create("Description").floating().setRows(1);

        ListGroup<TodoItem> todoItemsGroup = ListGroup.create();
        ListGroup<TodoItem> doneItemsGroup = ListGroup.create();

        Button addButton = Button.createPrimary("ADD");
        addButton.asElement().style.setProperty("min-width", "120px");

        addButton.addClickListener(evt -> {
            if (!title.isEmpty() && !description.isEmpty()) {
                TodoItem todoItem = new TodoItem(title.getValue(), description.getValue());

                ListItem<TodoItem> listItem = todoItemsGroup.createItem(todoItem, todoItem.description).setHeading(todoItem.title);

                IconButton doneButton = IconButton.create(Icons.ALL.check());
                doneButton.addClickListener(clickEvent -> {
                    clickEvent.stopPropagation();
                    todoItemsGroup.removeItem(listItem);
                    doneItemsGroup.appendItem(listItem);
                    doneButton.asElement().remove();
                });

                doneButton.setButtonType(StyleType.SUCCESS);
                doneButton.asElement().style.setProperty("position", "absolute");
                doneButton.asElement().style.setProperty("top", "10px");
                doneButton.asElement().style.setProperty("right", "10px");

                listItem.appendContent(doneButton
                        .asElement());

                todoItemsGroup.appendItem(listItem);

                Tooltip.create(doneButton.asElement(), "Mark Done");
            }
        });

        layout.getContentPanel().appendChild(Card.create("NEW TODO", "Add a new todo list item")
                .appendContent(title.asElement())
                .appendContent(description.asElement())
                .appendContent(addButton.asElement())
                .asElement());

        layout.getContentPanel().appendChild(Card.create("TODO ITEMS")
                .appendContent(todoItemsGroup.asElement())
                .asElement());

        layout.getContentPanel().appendChild(Card.create("DONE ITEMS")
                .appendContent(doneItemsGroup.asElement())
                .asElement());
    }

    private class TodoItem {

        private String title;
        private String description;

        public TodoItem(String title, String description) {
            this.title = title;
            this.description = description;
        }
    }
}
