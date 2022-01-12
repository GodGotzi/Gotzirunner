package at.gotzi.itsmanager.view;

public interface ItemView {

    void build();

    void init();

    void onButtonAction(Action action, ItemView itemView);

    enum Action {
        Delete,
        Edit,
        Info
    }
}