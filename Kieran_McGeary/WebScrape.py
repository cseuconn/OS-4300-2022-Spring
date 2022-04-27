import scraper_api
from os import system


def display_help():
    print('Commands:')
    print('time: displays running export time')
    print('samples: displays current number of samples taken')
    print('tickers: displays ticker list')
    print('prices: displays the price for each symbol')
    print('names: displays the full name of all tickers')
    print('start: begins exporting price values to CSV')
    print('stop: ends CSV export')
    print('clear: clears the terminal')
    print('quit: stops sampling and exits')


if __name__ == '__main__':
    count = 0
    while True:
        tick = input('Enter a ticker (nothing if done): ')
        if tick == '': break  # done getting inputs
        else:
            # Check if our ticker is actually valid
            test = scraper_api.get_price(tick)
            if test == -1: exit(-1)  # Something went wrong
            elif test is None: continue  # Not a valid ticker
            else: scraper_api.tick_list_append(tick)
    if scraper_api.tick_list_len() == 0: exit(0)

    while True:
        cmd = input('Enter a command (? for help): ')
        match cmd:
            case 'quit':
                scraper_api.done()
                break
            case 'time':
                t = scraper_api.display_time()
                if t == -1: print('Not exporting')
                else: print('Runtime: ' + str(t) + ' s')
            case 'names':
                names = scraper_api.display_names()
                tick_list = scraper_api.display_tickers()
                for i in range(len(names)):
                    print(tick_list[i] + ': ' + names[i])
            case 'samples':
                s = scraper_api.display_samples()
                if s == -1: print('Not exporting')
                else: print('Samples: ' + str(s))
            case 'tickers':
                tick_list = scraper_api.display_tickers()
                for t in tick_list:
                    print(t)
            case 'prices':
                prices = scraper_api.display_prices()
                tick_list = scraper_api.display_tickers()
                for i in range(len(prices)):
                    print(tick_list[i] + ': ' + prices[i])
            case 'start':
                name = input("Enter file name (will be created if missing): ")
                scraper_api.start_recording(name)
            case 'stop': scraper_api.stop_recording()
            case 'clear': system('clear')
            case '?': display_help()
            case _: continue
