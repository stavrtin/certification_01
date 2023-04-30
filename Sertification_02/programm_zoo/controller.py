import view
import model


def process_data():
    print('поехали')

    user_select = view.show_menu()
    # logging.info(f"выбран пункт {user_select}")
    if user_select == 1:
        print('Просмотр списка питомцев')
        view.views_pets()

    elif user_select == 2:
        print('Выбран пункт - Добавить новое животное')
        view.add_pets()

    elif user_select == 3:
        print('Выбран пункт - Показать список команд, которые выполняет животное')
        view.views_commands_pets()

    elif user_select == 4:
        print('Выбран пункт - Обучить животное новой команде')
        view.add_commands_pets()

    else:
        print('exit')
        exit()
