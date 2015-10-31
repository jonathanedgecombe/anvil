include Makefile.rc

STEAMCMD=$(STEAMCMD_DIR)/steamcmd.sh
STEAMCMD_DIR=steamcmd
STEAMCMD_ARCHIVE=$(STEAMCMD_DIR)_linux.tar.gz
STEAMCMD_URL=https://steamcdn-a.akamaihd.net/client/installer/$(STEAMCMD_ARCHIVE)

WURM_DIR=wurm
WURM_DONE=$(WURM_DIR).done

CLIENT_DIR=$(WURM_DIR)/client
CLIENT_DONE=$(CLIENT_DIR).done
CLIENT_APPID=366220
CLIENT_COMMON_JAR=$(CLIENT_DIR)/WurmLauncher/common.jar
CLIENT_JAR=$(CLIENT_DIR)/WurmLauncher/client.jar

SERVER_DIR=$(WURM_DIR)/server
SERVER_DONE=$(SERVER_DIR).done
SERVER_APPID=402370
SERVER_COMMON_JAR=$(SERVER_DIR)/common.jar
SERVER_JAR=$(SERVER_DIR)/server.jar

# use the server's common.jar wherever we use common.jar
# the wurm.done target checks the client's common.jar is identical before
# proceeding
COMMON_JAR=$(SERVER_COMMON_JAR)

SRC_DIR=src
SRC_DONE=$(SRC_DIR).done

SRC_CLIENT_DIR=$(SRC_DIR)/client
SRC_CLIENT_DONE=$(SRC_CLIENT_DIR).done

SRC_COMMON_DIR=$(SRC_DIR)/common
SRC_COMMON_DONE=$(SRC_COMMON_DIR).done

SRC_SERVER_DIR=$(SRC_DIR)/server
SRC_SERVER_DONE=$(SRC_SERVER_DIR).done

.PHONY: all clean

all: $(SRC_DONE)

clean:
	$(RM) $(SRC_DONE) $(WURM_DONE) $(CLIENT_DONE) $(SERVER_DONE)
	$(RM) -r $(SRC_DIR)

# download steamcmd
$(STEAMCMD_ARCHIVE):
	wget -qO $@ $(STEAMCMD_URL)

# extract steamcmd
$(STEAMCMD): $(STEAMCMD_ARCHIVE)
	mkdir -p $(STEAMCMD_DIR)
	tar -C $(STEAMCMD_DIR) -xzf $(STEAMCMD_ARCHIVE)

# download wurm client
$(CLIENT_DONE): $(STEAMCMD)
	mkdir -p $(CLIENT_DIR)
	$(STEAMCMD) +login $(STEAM_USER) +force_install_dir ../$(CLIENT_DIR) \
		+@sSteamCmdForcePlatformType windows +app_update $(CLIENT_APPID) +quit
	touch $@

# download wurm server
$(SERVER_DONE): $(STEAMCMD)
	mkdir -p $(SERVER_DIR)
	$(STEAMCMD) +login $(STEAM_USER) +force_install_dir ../$(SERVER_DIR) \
		+app_update $(SERVER_APPID) +quit
	touch $@

# check common.jar is identical across client and server
$(WURM_DONE): $(CLIENT_DONE) $(SERVER_DONE)
	cmp -s $(CLIENT_COMMON_JAR) $(SERVER_COMMON_JAR)
	touch $@

# decompile common.jar
$(SRC_COMMON_DONE): $(WURM_DONE)
	mkdir -p $(SRC_COMMON_DIR)
	java -cp $(CFR_JAR) org.benf.cfr.reader.Main $(COMMON_JAR) \
		--outputdir $(SRC_COMMON_DIR)
	touch $@

# decompile client.jar
$(SRC_CLIENT_DONE): $(WURM_DONE)
	mkdir -p $(SRC_CLIENT_DIR)
	java -cp $(CFR_JAR):$(COMMON_JAR) org.benf.cfr.reader.Main \
		$(CLIENT_JAR) --outputdir $(SRC_CLIENT_DIR)
	touch $@

# decompile server.jar
$(SRC_SERVER_DONE): $(WURM_DONE)
	mkdir -p $(SRC_SERVER_DIR)
	java -cp $(CFR_JAR):$(COMMON_JAR) org.benf.cfr.reader.Main \
		$(SERVER_JAR) --outputdir $(SRC_SERVER_DIR)
	touch $@

$(SRC_DONE): $(SRC_COMMON_DONE) $(SRC_CLIENT_DONE) $(SRC_SERVER_DONE)
	touch $@
