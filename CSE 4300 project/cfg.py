import threading

# global variables
t = 0  # time counter in seconds
samples = 0  # sample counter for CSV export
interval = 10  # sampling interval
ticker_list = []  # list of user-input tickers and prices
out = []  # list of CSVs
threads = []  # thread id list
done = 0  # flag for shutdown condition
recording = 0  # flag for tracking CSV export
file = 0  # temporary, to be replaced by file pointer
file_lock = threading.Lock()  # mutex lock for CSV file pointer
file_cond = threading.Condition(file_lock)  # condition variable for CSV write order
turn = 0  # counter for CSV write synchronization
bar = 0  # placeholder for time consistency
