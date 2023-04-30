from mysql.connector import connect, Error

import model


def show_menu():
    menu = {1: 'Просмотр базы питомцев',
                 2: 'Добавить новое животное',
                 3: 'Показать список команд, которые выполняет животное',
                 4: 'Обучить животное новой команде',
                 5: 'Выход'
                 }

    for key, value in menu.items():
        print(f'{key} - {value}')
    while True:
        try:
            item_menu = int(input("Выберете действие..."))
            if item_menu not in menu:
                raise Exception
            return item_menu
        except Exception:
            print('Ошибка выбора (( ')


def views_pets():
    '''подключаемся к БД - Animals
    смотрим список питомцев '''

    db = connect(host="localhost",    # your host, usually localhost
                         user="root",         # your username
                         passwd="1234",  # your password
                         db="animals")        # name of the data base

    cur = db.cursor()
    cur.execute("SELECT * FROM pet")
    print(f'Имя - возраст - и тд')
    for row in cur.fetchall():
        print(row)
    db.close()

def add_pets():
    '''подключаемся к БД - Animals
     '''
    name = model.add_new_pet()
    print(f'тут пропишем команду на добавление питомца c кличкой {name}')

def views_commands_pets():
    '''подключаемся к БД - Animals
    смотрим список commands питомцев '''

    db = connect(host="localhost",    # your host, usually localhost
                         user="root",         # your username
                         passwd="1234",  # your password
                         db="animals")        # name of the data base

    cur = db.cursor()
    # cur.execute("SELECT * FROM pet")
    cur.execute("select * from (select * from animals.pet_commands pc join animals.commands c on pc.id_commands = c.id )\
     jc join (select id, name, date_born, id_types from animals.pet where name = 'ресси' ) iz where id_pet = iz.id;")
    print(f'Список команд ')
    for row in cur.fetchall():
        print(row)
    db.close()


def add_commands_pets():
    '''подключаемся к БД - Animals
     '''
    name_com = model.add_new_command()
    print(f'тут пропишем НОВУЮ команду {name_com}, которую будет выполнять питомец')
