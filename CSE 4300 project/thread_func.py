from time import sleep
import csv
import cfg
import scraper_api


def scraper(ticker, i):  # scraper functionality
    writer = csv.writer(cfg.file)
    while cfg.recording:
        price = scraper_api.get_price(ticker)
        cfg.file_lock.acquire()
        while cfg.turn != i: cfg.file_cond.wait()
        cfg.out[i] = price
        cfg.turn += 1
        if cfg.turn == len(cfg.ticker_list):
            cfg.samples += 1
            cfg.turn = 0
            writer.writerow([cfg.samples] + cfg.out)
            if cfg.recording == 0:
                cfg.file.close()
                cfg.samples = 0
        cfg.file_cond.notify_all()
        cfg.file_lock.release()
        cfg.bar.wait()


def timer():
    while cfg.recording:
        for i in range(cfg.interval):
            sleep(1)
            if cfg.done: break
            cfg.t += 1
        cfg.bar.wait()
