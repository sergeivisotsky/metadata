#!/bin/bash
git config alias.wsbackmerge '!git pull --rebase && git rebase master && git push --force-with-lease'