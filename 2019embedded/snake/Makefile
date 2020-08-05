CC = arm-linux-gcc

OBJS = main.o

APP = snake_LCD

all : $(APP)

$(APP) : $(OBJS)
    $(CC) -o $@ $^

clean:
    rm -f $(OBJS) $(APP)