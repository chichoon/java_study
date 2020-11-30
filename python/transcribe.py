# -*- coding: utf-8 -*-
import os
import shutil
import glob
from subprocess import call
import speech_recognition as sr
from tqdm import tqdm
from multiprocessing.dummy import Pool
import time

pool = Pool(8)  # Number of concurrent threads
r = sr.Recognizer()  # speech_recognition recognizer r에 저장

with open("api-key.json") as f:  # 구글 클라우드 인증키 불러오기
    GOOGLE_CLOUD_SPEECH_CREDENTIALS = f.read()  # 해당 변수에 인증키 저장

def copy(dir1, dir2, rm=True):
    if not os.path.exists(dir2):
        shutil.copytree(dir1, dir2)
    if rm:
        shutil.rmtree(dir2) 
        shutil.copytree(dir1, dir2)
#noisy_TIMIT 폴더를 하위경로와 함께 통째로 복사하기 위한 함수

direction = input("input the folder noisefile contained :")

curr_path = os.path.join(os.path.abspath(os.getcwd()), direction)
txt_path = os.path.join(os.path.abspath(os.getcwd()), 'tmp_filelist.txt')
copy_path = os.path.join(os.path.abspath(os.getcwd()), 'rawfile')  # rawfile 폴더 생성용

copy(curr_path, copy_path, rm=True)

for root, dirs, files in os.walk(copy_path):
    if files:
        for file in files:
            if os.path.splitext(file)[1] == '.snd' :
                newname = os.path.splitext(file)[0] + '.raw'
                os.rename(os.path.join(root,file), os.path.join(root, newname))

with open(txt_path,'wt') as f:
    for root, dirs, files in os.walk(copy_path):
        if files:
            for file in files:
                if file.find("filelist") < 0:
                    filename = os.path.join(root, file)
                    f.write(filename + '\n')
                        
call(["sort","tmp_filelist.txt","-o", os.path.join(os.path.abspath(os.getcwd()), 'filelist.txt')]) 
call(["rm","-rf",txt_path])

try:
    with open('filelist.txt', 'r') as file: #파일리스트를 연다
        lines = file.readlines() #모든 줄을 lines에 리스트의 원소로 전부 저장한다
        lines.sort() #리스트를 사전순 정렬한다
        for line in lines: #txt파일의 모든 줄에 대하여 (경로)
            temp = os.path.splitext(line)
            temp2 = [temp[0], '.raw']  # .raw\n 제거 후 .raw 붙이기
            origfile = ''.join(temp2)
            temp2 = [temp[0], '.wav']  # .raw\n 제거 후 .wav 붙이기  # 해당 줄의 경로 split
            newfile = ''.join(temp2)
            call(["sox","-r","8000", "-e", "signed-integer", "-c", "1", "-b", "16", origfile, newfile]) 
            
    for root, dirs, files in os.walk(copy_path): #rawfile 내의 모든 파일 검색
        if files:
            for file in files: #모든 파일에 대해서
                if os.path.splitext(file)[1] in ['.raw', '.RAW']: #확장자가 raw이면
                    os.remove(os.path.join(root,file)) #삭제

    with open(txt_path,'wt') as f:
        for root, dirs, files in os.walk(copy_path):
            if files:
                for file in files:
                    if file.find("filelist") < 0:
                        filename = os.path.join(root, file)
                        f.write(filename + '\n')

        call(["sort","tmp_filelist.txt","-o", os.path.join(os.path.abspath(os.getcwd()), 'filelist.txt')]) 
        call(["rm","-rf",txt_path])

except FileNotFoundError:
    print("The folder name is invalid")

#transcribe start
def transcribe(file):  # Transcribe 함수 선언
    print(os.path.abspath(file) + " started" + '\n')

    with sr.AudioFile(os.path.abspath(file)) as source:  # name 경로에 해당하는 오디오 파일을 source로 open하여
        audio = r.record(source)  # audio는 source가 담고 있는 파일을 record

    try:
        # text = r.recognize_google_cloud(audio, credentials_json=GOOGLE_CLOUD_SPEECH_CREDENTIALS)
        text = r.recognize_google_cloud(audio, credentials_json=GOOGLE_CLOUD_SPEECH_CREDENTIALS, language="ko-KR")
        print(os.path.abspath(file) + " done")
        dirtext = [os.path.abspath(file), " : ", text, "\n"]
        ''.join(dirtext)
        return dirtext
    except sr.UnknownValueError:  # 값을 읽어올 수 없을 때
        print("Google Speech Recognition could not understand file")
        dirtext = [os.path.abspath(file), " : -\n"]
        ''.join(dirtext)
        return dirtext
    except sr.RequestError as e:  # Google Speech에 접속할 수 없을때
        print("Could not request results from Google Speech Recognition service; {0}".format(e))
        dirtext = [os.path.abspath(file), " : -\n"]
        ''.join(dirtext)
        return dirtext
    except sr.HTTPError as e:  # Http 에러
        print("Couldn't connect to the websites perhaps , Hyper text transfer protocol error", e)
        dirtext = [os.path.abspath(file), " : -\n"]
        ''.join(dirtext)
        return dirtext

start = time.time()  # 시작 시간 저장

with open('transcribe.txt', 'w') as f:
    with open('filelist.txt', 'r') as file:
        modifl = []
        lines = file.readlines()
        lines.sort()
        for line in lines:
            temp = os.path.splitext(line)
            temp = [temp[0], '.wav']
            line = ''.join(temp)
            modifl.append(line) #맨 끝의 \n을 없애기 위한 작업
        modifl.sort()
        all_text = pool.map(transcribe, modifl)
        for l in all_text:
            f.write(''.join(l))

print("time :", time.time() - start)
call(["rm","-rf",os.path.join(os.path.abspath(os.getcwd()), 'filelist.txt')])
call(["rm","-rf",copy_path])
pool.close()  # 리소스 방지를 위한 close 호출
pool.join()  # 작업 완료 대기를 위한 join 호출
    
