import cfg
import requests
import threading
import csv
import yfinance as yf
import thread_func


def get_ticker(ticker):
    stock = yf.Ticker(ticker)
    return stock


def get_name(ticker):
    stock = yf.Ticker(ticker)
    try:
        return stock.info['shortName']
    except requests.exceptions.ConnectionError:
        print('Fatal connection error')
        return -1


def get_price(ticker):
    stock = yf.Ticker(ticker)
    try:
        return stock.info['regularMarketPrice']
    except requests.exceptions.ConnectionError:
        print('Fatal connection error')
        return -1


def done():
    if cfg.recording: stop_recording()
    cfg.done = 1
    for i in cfg.threads: i.join()
    return 0


def display_prices():
    price_list = []
    for tick in cfg.ticker_list:
        price = get_price(tick)
        price_list.append(price)
    return price_list


def display_names():
    tick_list = []
    for tick in cfg.ticker_list:
        name = get_name(tick)
        tick_list.append(name)
    return tick_list


def display_time():
    if cfg.recording: return cfg.t
    else: return -1


def display_samples():
    if cfg.recording: return cfg.samples
    else: return -1


def display_tickers():
    return cfg.ticker_list


def start_recording(name):
    if cfg.recording:
        return -1
    else:
        cfg.file = open(name, 'w', newline='')
        writer = csv.writer(cfg.file)
        writer.writerow(['Sample'] + cfg.ticker_list)
        cfg.recording = 1
        cfg.bar = threading.Barrier(len(cfg.ticker_list) + 1)
        for i in range(len(cfg.ticker_list)):
            x = threading.Thread(target=thread_func.scraper, args=(cfg.ticker_list[i], i,))
            cfg.out.append(0)
            cfg.threads.append(x)
            x.start()

        y = threading.Thread(target=thread_func.timer, args=())
        cfg.threads.append(y)
        y.start()
        return 0


def stop_recording():
    if not cfg.recording:
        return -1
    else:
        cfg.recording = 0
        cfg.t = 0
        for i in cfg.threads: i.join()
        return 0


def tick_list_append(tick):
    cfg.ticker_list.append(tick)
    return 0


def tick_list_len():
    return len(cfg.ticker_list)
