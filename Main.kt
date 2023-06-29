package machine

enum class Status {
    WAIT_FOR_ACTION,
    BUY,
    ADD_WATER,
    ADD_MILK,
    ADD_BEANS,
    ADD_CUPS
}


object CoffeeMachine {
    private var water: Int = 400
    private var milk: Int = 540
    private var beans: Int = 120
    private var cups: Int = 9
    private var money: Int = 550
    private var status: Status = Status.WAIT_FOR_ACTION

    private fun outputStatus() {
        println()
        println("The coffee machine has:")
        println("$water of water")
        println("$milk of milk")
        println("$beans of coffee beans")
        println("$cups of disposable cups")
        println("$$money of money")
        status = Status.WAIT_FOR_ACTION
        println()
    }

    private fun takeMoney() {
        println("I gave you $$money")
        money = 0
        println()
    }



    private fun buy(action: String) {
        if (cups == 0) {
            println("Sorry, not enough cups")
            return
        }

        val cup = when (action) {
            "1" -> listOf(250, 0, 16, 4)
            "2" -> listOf(350, 75, 20, 7)
            else -> listOf(200, 100, 12, 6)
        }

        if (cup[0] > water) {
            println("Sorry, not enough water")
            return
        }

        if (cup[1] > milk) {
            println("Sorry, not enough milk")
            return
        }

        if (cup[2] > beans) {
            println("Sorry, not enough beans")
            return
        }

        println("I have enough resources, making you a coffee!")
        money += cup[3]
        water -= cup[0]
        milk -= cup[1]
        beans -= cup[2]
        cups -= 1
        println()
    }


    fun makeAction(action: String) {
        when (status) {
            Status.WAIT_FOR_ACTION -> {
                when (action) {
                    "remaining" -> {
                        outputStatus()
                        print("Write action (buy, fill, take, remaining, exit): ")
                    }
                    "buy" -> {
                        status = Status.BUY
                        println()
                        print("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: ")
                    }
                    "fill" -> {
                        status = Status.ADD_WATER
                        println()
                        print("Write how many ml of water do you want to add: ")
                    }
                    "take" -> takeMoney()
                }
            }
            Status.BUY -> {
                when (action) {
                    "1", "2", "3" -> buy(action)
                }
                status = Status.WAIT_FOR_ACTION
                print("Write action (buy, fill, take, remaining, exit): ")
            }
            Status.ADD_WATER -> {
                water += action.toInt()
                status = Status.ADD_MILK
                print("Write how many ml of milk do you want to add: ")
            }
            Status.ADD_MILK -> {
                milk += action.toInt()
                status = Status.ADD_BEANS
                print("Write how many grams of coffee beans do you want to add: ")
            }
            Status.ADD_BEANS -> {
                beans += action.toInt()
                status = Status.ADD_CUPS
                print("Write how many disposable cups of coffee do you want to add: ")
            }
            Status.ADD_CUPS -> {
                cups += action.toInt()
                status = Status.WAIT_FOR_ACTION
                println()
            }
        }
    }
}


fun main() {
    print("Write action (buy, fill, take, remaining, exit): ")
    do {
        val action = readLine()!!
        CoffeeMachine.makeAction(action)
    } while (action != "exit")
}
