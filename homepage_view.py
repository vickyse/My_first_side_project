import tkinter as tk
from model import *


class text_in_homepage:
    """
    主畫面的選項，屬性有其所屬窗口(master_frame)、文字、以及是否被選取(預設為false)
    """

    def __init__(self, master_frame: tk.Tk, text: str) -> None:
        """初始化"""
        self.master = master_frame
        self.text = text
        if self.get_text() == "表單":
            self.is_selected = True
        else:
            self.is_selected = False

    def get_text(self) -> str:
        """返回當前實例的文字"""
        return self.text

    def check_selected(self) -> bool:
        """返回是否被選取"""
        return self.is_selected

    def select(self) -> None:
        """選取，若已選取，則不動作"""
        if self.is_selected == False:
            self.is_selected = True
            return None
        elif self.is_selected == True:
            return None


class homepage:
    """
    主畫面，屬性有所屬窗口、在這個專案中的主畫面中有的三個選項
    """

    def __init__(self, frame: tk.Tk) -> None:
        """初始化homepage"""
        self.master_frame = frame
        self.text_option_1 = text_in_homepage(self.master_frame, "表單")
        self.text_option_2 = text_in_homepage(self.master_frame, "查看、修改數據")
        self.text_option_3 = text_in_homepage(self.master_frame, "離開")
        self.option_list = []
        self.option_list.append(self.text_option_1)
        self.option_list.append(self.text_option_2)
        self.option_list.append(self.text_option_3)
        self.position_counted = 0
        self.initialise_gui()  # 初始化GUI

    def initialise_gui(self) -> None:
        """初始化homepage的GUI"""
        # 獲取當前載具螢幕的寬度和高度
        screen_width = self.master_frame.winfo_screenwidth()
        screen_height = self.master_frame.winfo_screenheight()

        # 窗口設置為與螢幕同大
        self.master_frame.geometry(f"{screen_width}x{screen_height}")
        self.master_frame.title("翔昱工程")  # 設置窗口標題
        self.master_frame.configure(bg="black")  # 背景黑色

        label_text = "翔昱工程"
        label = tk.Label(
            self.master_frame, text=label_text, font=("Helvetica", 120), bg="black"
        )
        label.place(relx=0.5, rely=0.15, anchor="center")

        # 添加橫線
        line = tk.Canvas(
            self.master_frame, width=int(screen_width * 0.9), height=1, bg="black"
        )
        line.place(relx=0.5, rely=0.25, anchor="center")
        self.place_all_option(self.option_list)  # 放置三個主選單選項(預設"表單"為已選取)

        # 實作窗口的選取功能
        self.master_frame.bind("<Up>", self.option_move_up)
        self.master_frame.bind("<Down>", self.option_move_down)

    def place_all_option(self, all_option: list[text_in_homepage]) -> None:
        """將三個選項放入到主窗口"""
        rely_value = 0.4
        for option in all_option:
            if option.is_selected:
                option_Label = tk.Label(
                    self.master_frame,
                    text=option.get_text(),
                    font=("Helvetica", 96),
                    bg="lightgreen",
                )
                option_Label.place(relx=0.5, rely=rely_value, anchor="center")
            else:
                option_Label = tk.Label(
                    self.master_frame,
                    text=option.get_text(),
                    font=("Helvetica", 96),
                    bg="black",
                )
                option_Label.place(relx=0.5, rely=rely_value, anchor="center")
            rely_value += 0.23  # rely的數值加大，選項放的位置就更下面

    def option_move_up(self, event) -> None:
        """按下方向鍵上的方法"""
        self.position_counted -= 1
        self.redraw()

    def option_move_down(self, event) -> None:
        """按下方向鍵下的方法"""
        self.position_counted += 1
        self.redraw()

    def clear_frame(self) -> None:
        """清除主畫面，用於redraw主畫面"""
        for widget in self.master_frame.winfo_children():
            widget.destroy()

    def redraw(self) -> None:
        """重畫主畫面，當使用者操作方向鍵以選取主畫面選項時使用"""
        self.clear_frame()
        for option in self.option_list:
            option.is_selected = False
        remainder = self.position_counted % 3
        self.option_list[remainder].select()
        self.initialise_gui()


def call_homepage(root: tk.Tk) -> None:
    homepage(root)
    root.mainloop()


def main() -> None:
    call_homepage(tk.Tk())


if __name__ == "__main__":
    main()
