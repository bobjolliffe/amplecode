﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml;
using System.IO;

namespace IPExport
{
    /// <summary>
    ///  This class is responsible for converting a SVX model into XML and write the content to the given MemoryStream.
    /// </summary>
    class SvxMarshaller
    {
        public void marshal(Svx svx, MemoryStream stream)
        {
            //XmlWriter writer = new XmlTextWriter(ExportConstants.EXPORT_FILENAME, null); // For debugging

            XmlWriter writer = new XmlTextWriter(stream, null);
            
            writer.WriteStartDocument();

            writer.WriteStartElement("svx");
            writer.WriteAttributeString("xmlns", ExportConstants.XMLNS);
            writer.WriteAttributeString("xmlns:xsi", ExportConstants.XSI);
            writer.WriteAttributeString("xsi:schemaLocation", ExportConstants.XSI_LOCATION);

            writer.WriteStartElement("events");

            foreach (Event event_ in svx.Events)
            {
                writer.WriteStartElement("event");
                writer.WriteAttributeString("code", event_.Code);
                writer.WriteAttributeString("date", event_.Date);
                writer.WriteAttributeString("location", event_.Location);
                writer.WriteAttributeString("homeTeam", event_.HomeTeam);
                writer.WriteAttributeString("awayTeam", event_.AwayTeam);
                writer.WriteEndElement();
            }

            writer.WriteEndElement();

            writer.WriteStartElement("clips");

            foreach (Clip clip in svx.Clips)
            {
                writer.WriteStartElement("clip");
                writer.WriteAttributeString("offset", Convert.ToString( clip.Offset ));
                writer.WriteAttributeString("team", clip.Team);
                writer.WriteAttributeString("filename", clip.Filename);
                writer.WriteAttributeString("person", clip.Person);
                writer.WriteAttributeString("event", clip.Event);

                if (clip.Categories != null && clip.Categories.Count > 0)
                {
                    writer.WriteStartElement("categories");

                    foreach (string category in clip.Categories)
                    {
                        writer.WriteStartElement("category");
                        writer.WriteString(category);
                        writer.WriteEndElement();
                    }

                    writer.WriteEndElement();
                }
                
                writer.WriteEndElement();
            }

            writer.WriteEndElement();

            writer.WriteEndElement();

            writer.WriteEndDocument();
            writer.Close();            
        }
    }
}