class Part2:
    counter = 0
    numbers = list()

    def __init__(self):
        file = open("input.txt", "r")

        line = file.read()
        self.numbers = line.split(',')
        for i in range(len(self.numbers)):
            self.numbers[i] = int(self.numbers[i])

    def calc(self, input):
        i = 0
        while True:
            one = self.numbers[i]
            parse_instruction = [int(str(one)[j]) for j in range(len(str(one)) - 1, -1, -1)]
            address_modes = []
            opcode = parse_instruction[0]

            if one == 99:
                break

            for j in parse_instruction[2:]:
                address_modes.append(j)
            while len(address_modes) < 3:
                address_modes.append(0)

            two = self.numbers[i + 1]
            three = self.numbers[i + 2]
            four = self.numbers[i + 3]

            value1 = i + 1 if address_modes[0] == 1 else two
            value2 = i + 2 if address_modes[1] == 1 else three
            value3 = i + 3 if address_modes[2] == 1 else four

            print(f'task: {one}, opcode: {opcode}, modes: {address_modes}, a: {value1},b: {value2},c: {value3}')

            if opcode == 1:
                print(f"+1+{self.numbers[value1]}.{self.numbers[value2]}>{self.numbers[value1] + self.numbers[value2]}")
                self.numbers[value3] = self.numbers[value1] + self.numbers[value2]
                i += 4
            elif opcode == 2:
                print(f"+2+{self.numbers[value1]}.{self.numbers[value2]}>{self.numbers[value1] * self.numbers[value2]}")
                self.numbers[value3] = self.numbers[value1] * self.numbers[value2]
                i += 4
            elif opcode == 3:
                print(f"+3+{self.numbers[value1]}.{input}")
                self.numbers[two] = input
                i += 2
            elif opcode == 4:
                print(self.numbers[two])
                i += 2
            elif opcode == 5:
                if self.numbers[value1] != 0:
                    i = self.numbers[value2]
                else:
                    i += 3
                print(f"+5+{self.numbers[value1]}.{self.numbers[value2]}>{str(self.numbers[value1] != 0).lower()}>{i}")
            elif opcode == 6:
                if self.numbers[value1] == 0:
                    i = self.numbers[value2]
                else:
                    i += 3

                print(f"+6+{self.numbers[value1]}.{self.numbers[value2]}>{str(self.numbers[value1] == 0).lower()}>{i}")
            elif opcode == 7:
                print(f"+7+{self.numbers[value1]}.{self.numbers[value2]}>{str(self.numbers[value1] <self.numbers[value1]).lower()}")
                i += 4
                self.numbers[value3] = 1 if self.numbers[value1] < self.numbers[value2] else 0
            elif opcode == 8:
                i += 4
                print(f"+8+{self.numbers[value1]}.{self.numbers[value2]}>{str(self.numbers[value1] == self.numbers[value2]).lower()}")
                self.numbers[value3] = 1 if self.numbers[value1] == self.numbers[value2] else 0


part2 = Part2()
part2.calc(5)